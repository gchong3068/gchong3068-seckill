package com.gchong3068.seckill.order.service;

import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.order.model.dto.SeckillOrderMqDTO;
import com.gchong3068.seckill.order.model.vo.DoSeckillReqVO;
import com.gchong3068.seckill.order.model.vo.DoSeckillRspVO;
import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultReqVO;
import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultRspVO;

/**
 * @Author: gchong3068
 * @Date: 2026/5/23 9:29
 * @Version: v1.0.0
 * @Description: 订单模块业务
 **/
public interface OrderService {


    /**
     * 秒杀下单
     * @param reqVO
     * @return
     */
    Response<DoSeckillRspVO> doSeckill(DoSeckillReqVO reqVO);

    /**
     * 异步消费下单信息: 扣减库存 + 创建订单
     * @author gchong3068
     * @date 2026/6/21 20:47
     * @param message
     */
    void createSeckillOrder(SeckillOrderMqDTO message);


    /**
     * 查询秒杀订单处理结果
     * @author gchong3068
     * @date 2026/7/17 14:17
     * @param reqVO
     * @return com.gchong3068.seckill.common.utils.Response<com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultRspVO>
     */
    Response<FindSeckillOrderResultRspVO> findSeckillOrderResult(FindSeckillOrderResultReqVO reqVO);



}
