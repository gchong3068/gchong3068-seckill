package com.gchong3068.seckill.order.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.gchong3068.seckill.common.config.RabbitMQConfig;
import com.gchong3068.seckill.common.constant.RedisKeyConstants;
import com.gchong3068.seckill.common.domain.dataobject.GoodsDO;
import com.gchong3068.seckill.common.domain.dataobject.SeckillActivityDO;
import com.gchong3068.seckill.common.domain.dataobject.SeckillGoodsDO;
import com.gchong3068.seckill.common.domain.dataobject.SeckillOrderDO;
import com.gchong3068.seckill.common.domain.mapper.GoodsDOMapper;
import com.gchong3068.seckill.common.domain.mapper.SeckillActivityDOMapper;
import com.gchong3068.seckill.common.domain.mapper.SeckillGoodsDOMapper;
import com.gchong3068.seckill.common.domain.mapper.SeckillOrderDOMapper;
import com.gchong3068.seckill.common.enums.ResponseCodeEnum;
import com.gchong3068.seckill.common.exception.BizException;
import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.order.enums.OrderStatusEnum;
import com.gchong3068.seckill.order.model.dto.SeckillOrderMqDTO;
import com.gchong3068.seckill.order.model.vo.DoSeckillReqVO;
import com.gchong3068.seckill.order.model.vo.DoSeckillRspVO;
import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultReqVO;
import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultRspVO;
import com.gchong3068.seckill.order.mq.SeckillOrderMessageSender;
import com.gchong3068.seckill.order.service.OrderService;
import com.gchong3068.seckill.order.utils.OrderLockUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @Author: gchong3068
 * @Date: 2026/5/23 9:30
 * @Version: v1.0.0
 * @Description: TODO
 **/
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    @Resource
    private SeckillActivityDOMapper seckillActivityDOMapper;

    @Resource
    private SeckillGoodsDOMapper seckillGoodsDOMapper;

    @Resource
    private GoodsDOMapper goodsDOMapper;

    @Resource
    private SeckillOrderDOMapper seckillOrderDOMapper;

    @Resource
    private TransactionTemplate transactionTemplate;

    @Resource
    private OrderLockUtils orderLockUtils;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private SeckillOrderMessageSender seckillOrderMessageSender;
    @Autowired
    private StringRedisTemplate stringRedisTemplate;


    /**
     * 秒杀下单
     *
     * @param reqVO
     * @return
     */
    @Override
    public Response<DoSeckillRspVO> doSeckill(DoSeckillReqVO reqVO) {

        // 活动 ID
        Long activityId = reqVO.getActivityId();
        // 商品 ID
        Long goodsId = reqVO.getGoodsId();

        // 1. 获取当前登录用户 ID
        long userId = StpUtil.getLoginIdAsLong();
        log.info("==> 当前登录用户 ID: {}", userId);

        // 应用层锁：防止同一用户并发重复下单
        // 构建锁 Key "userId:activityId:goodsId"
        String lockKey = userId + ":" + activityId + ":" + goodsId;

        // 尝试获取锁，获取失败，则说明该用户对该商品已经有请求在处理中
        if (!orderLockUtils.tryLock(lockKey)) {
            log.warn("==> 应用层锁拦截重复下单, userId: {}, activityId: {}, goodsId: {}", userId, activityId, goodsId);
            throw new BizException(ResponseCodeEnum.SECKILL_ORDER_PROCESSING);
        }

        try {
            //2. 校验活动是否存在
            SeckillActivityDO activityDO = seckillActivityDOMapper.selectByPrimaryKey(activityId);
            if (Objects.isNull(activityDO)) {
                throw new BizException(ResponseCodeEnum.SECKILL_ACTIVITY_NOT_EXIST);
            }

            //3. 校验秒杀活动时间
            LocalDateTime now = LocalDateTime.now();
            //活动是否开始
            if (now.isBefore(activityDO.getCreateTime())) {
                throw new BizException(ResponseCodeEnum.SECKILL_ACTIVITY_NOT_STARTED);
            }

            //活动是否结束
            if (now.isAfter(activityDO.getEndTime())) {
                throw new BizException(ResponseCodeEnum.SECKILL_ACTIVITY_ENDED);
            }

            //4. 根据活动 ID 及商品 ID 查询秒杀商品，校验次活动下商品是否存在
            SeckillGoodsDO seckillGoodsDO = seckillGoodsDOMapper.selectByActivityIdAndGoodsId(activityId, goodsId);
            if (Objects.isNull(seckillGoodsDO)) {
                throw new BizException(ResponseCodeEnum.SECKILL_GOODS_NOT_EXIST);
            }

            //5. 校验库存
            if (seckillGoodsDO.getSeckillStock() <= 0) {
                throw new BizException(ResponseCodeEnum.SECKILL_GOODS_SOLD_OUT);
            }

            //生成订单号
            String orderNo = IdUtil.getSnowflakeNextIdStr();

            //构建信息体
            SeckillOrderMqDTO seckillOrderMqDTO = SeckillOrderMqDTO.builder()
                    .userId(userId)
                    .activityId(activityId)
                    .seckillGoodsId(seckillGoodsDO.getId())
                    .seckillPrice(seckillGoodsDO.getSeckillPrice())
                    .goodsId(goodsId)
                    .orderNo(orderNo)
                    .requestTime(now)
                    .build();

            // 发送 MQ，内部会携带 CorrelationData(orderNo)，方便生产者确认回调定位消息
            seckillOrderMessageSender.send(seckillOrderMqDTO);


            // 记录异步处理状态，前端后续可以根据 orderNo 查询最终结果
            saveOrderStatus(userId, orderNo, OrderStatusEnum.PROCESSING.getStatus());

            //立即响参
            return Response.success(
                    DoSeckillRspVO.builder()
                            .orderNo(orderNo)
                            .status(OrderStatusEnum.PROCESSING.getStatus())
                            .build()
            );
        } finally {
            // 无论成功还是异常，都要释放锁
            orderLockUtils.unlock(lockKey);
        }
    }



    @Override
    public void createSeckillOrder(SeckillOrderMqDTO message) {
        Long activityId = message.getActivityId();
        Long goodsId = message.getGoodsId();
        Long userId = message.getUserId();
        String orderNo = message.getOrderNo();

        log.info("==> 消费秒杀下单消息, orderNo: {}, userId: {}, activityId: {}, goodsId: {}",
                orderNo, userId, activityId, goodsId);

        //6. 查询商品信息，用于冗余到订单
        GoodsDO goodsDO = goodsDOMapper.selectByPrimaryKey(goodsId);

        //订单过期时间 ： 当前时间 + 30min
        LocalDateTime expireTime = LocalDateTime.now().plusDays(30);

        //编程式事务
        try{
            SeckillOrderDO orderDO =  transactionTemplate.execute(status -> {
                //7. 扣减库存
                int count = seckillGoodsDOMapper.deductStock(message.getSeckillGoodsId());
                if (count == 0) {
                    log.info("==> 扣减库存失败，商品已售罄或已下架，OrderNo:{}" , orderNo);
                    throw new BizException(ResponseCodeEnum.SECKILL_GOODS_SOLD_OUT);
                }

                //8. 创建订单
                SeckillOrderDO order = SeckillOrderDO.builder()
                        .userId(userId)
                        .activityId(activityId)
                        .goodsId(goodsId)
                        .orderNo(orderNo)
                        .seckillPrice(message.getSeckillPrice())
                        .goodsName(goodsDO.getGoodsName())
                        .goodsImg(goodsDO.getGoodsImg())
                        .status(OrderStatusEnum.PENDING_PAYMENT.getStatus())
                        .expireTime(expireTime)
                        .isDeleted(0)
                        .createTime(LocalDateTime.now())
                        .updateTime(LocalDateTime.now())
                        .build();

                seckillOrderDOMapper.insert(order);
                return order;
            });

            //扣库存失败，更新Redis中订单状态为秒杀失败
            if (Objects.isNull(orderDO)){
                saveOrderStatus(userId, orderNo, OrderStatusEnum.SECKILL_FAILED.getStatus());
                return;
            }

            saveOrderStatus(userId, orderNo, OrderStatusEnum.PENDING_PAYMENT.getStatus());
            log.info("==> 异步秒杀下单成功, orderNo: {}", orderNo);
        } catch (DuplicateKeyException e){
            // 幂等兜底：order_no 唯一索引命中，说明是重复投递的消息
            // 直接当作成功处理，不再抛异常，避免消费者把消息无限重投
            log.warn("==> 重复消费秒杀消息，订单已存在，幂等返回, orderNo: {}", orderNo);

            // 不能直接写 PENDING_PAYMENT，避免把已支付、已取消等状态回退
            SeckillOrderDO existedOrderDO = seckillOrderDOMapper.selectByOrderNoAndUserId(orderNo, userId);
            if (Objects.nonNull(existedOrderDO)) {
                saveOrderStatus(userId, orderNo, existedOrderDO.getStatus());
            } else {
                log.warn("==> 重复消费命中唯一索引，但未查询到当前用户订单, orderNo: {}, userId: {}", orderNo, userId);
            }

        }
    }


    /**
     * 查询秒杀订单处理结果
     * @author gchong3068
     * @date 2026/7/17 14:17
     * @param reqVO
     * @return com.gchong3068.seckill.common.utils.Response<com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultRspVO>
     */
    @Override
    public Response<FindSeckillOrderResultRspVO> findSeckillOrderResult(FindSeckillOrderResultReqVO reqVO) {

        String orderNo = reqVO.getOrderNo();
        long userId = StpUtil.getLoginIdAsLong();

        //查询Redis 获取订单状态
        String redisKey = RedisKeyConstants.SECKILL_ORDER_STATUS_PREFIX + userId + ":" + orderNo;
        String orderStatus = stringRedisTemplate.opsForValue().get(redisKey);

        if(StrUtil.isNotBlank(orderStatus)){
            OrderStatusEnum statusEnum = OrderStatusEnum.getByStatus(Integer.valueOf(orderStatus));

            if (OrderStatusEnum.PROCESSING.equals(statusEnum)
                || OrderStatusEnum.PENDING_PAYMENT.equals(statusEnum)) {
                return Response.success(FindSeckillOrderResultRspVO.builder()
                        .orderNo(orderNo)
                        .status(statusEnum.getStatus())
                        .statusDesc(statusEnum.getDescription())
                        .build());
            }
        }
        // 如果是 PENDING_PAYMENT 待支付状态，继续回查订单表，返回完整订单信息
        SeckillOrderDO orderDO = seckillOrderDOMapper.selectByOrderNoAndUserId(orderNo, userId);

        // 如果表里没订单记录，可能消费者还没来得及消费，返回处理中...
        if (Objects.isNull(orderDO)) {
            return Response.success(FindSeckillOrderResultRspVO.builder()
                    .orderNo(orderNo)
                    .status(OrderStatusEnum.PROCESSING.getStatus())
                    .statusDesc(OrderStatusEnum.PROCESSING.getDescription())
                    .build());
        }

        return Response.success(FindSeckillOrderResultRspVO.builder()
                .orderId(orderDO.getId())
                .orderNo(orderDO.getOrderNo())
                .status(orderDO.getStatus())
                .statusDesc(OrderStatusEnum.getDescriptionByStatus(orderDO.getStatus()))
                .goodsId(orderDO.getGoodsId())
                .goodsName(orderDO.getGoodsName())
                .goodsImg(orderDO.getGoodsImg())
                .seckillPrice(orderDO.getSeckillPrice())
                .build());
    }

    /**
     * 保存秒杀订单异步处理状态
     * @author gchong3068
     * @date 2026/7/17 14:02
     * @param userId
     * @param orderNo
     * @param status
     */
    private void saveOrderStatus(Long userId,String orderNo,Integer status){
        String redisKey = RedisKeyConstants.SECKILL_ORDER_STATUS_PREFIX + userId + ":" +orderNo;
        stringRedisTemplate.opsForValue().set(redisKey,String.valueOf(status),
                RedisKeyConstants.SECKILL_ORDER_STATUS_TTL_MINUTES, TimeUnit.MINUTES);
    }
}