package com.gchong3068.seckill.order.controller;

import cn.dev33.satoken.stp.StpUtil;
import com.gchong3068.seckill.common.aspect.ApiOperationLog;
import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.order.model.vo.*;
import com.gchong3068.seckill.order.service.OrderService;
import com.gchong3068.seckill.order.service.SeckillOrderResultNotifyService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

/**
 * @Author: gchong3068
 * @Date: 2026/5/23 9:36
 * @Version: v1.0.0
 * @Description: 订单模块
 **/
@RestController
@RequestMapping("/seckill/order")
@Slf4j
public class OrderController {

    @Resource
    private OrderService orderService;
    @Resource
    private SeckillOrderResultNotifyService seckillOrderResultNotifyService;


    /**
     * 秒杀下单
     * @author gchong3068
     * @date 2026/7/17 14:47
     * @param doSeckillReqVO
     * @return com.gchong3068.seckill.common.utils.Response<com.gchong3068.seckill.order.model.vo.DoSeckillRspVO>
     */
    @PostMapping
    @ApiOperationLog(description = "秒杀下单")
    public Response<DoSeckillRspVO> doSeckill(@RequestBody @Validated DoSeckillReqVO doSeckillReqVO) {
        return orderService.doSeckill(doSeckillReqVO);
    }

    /**
     * 查询秒杀订单处理结果
     *
     * @param reqVO
     * @return
     */
    @PostMapping("/result")
    @ApiOperationLog(description = "查询秒杀订单处理结果")
    public Response<FindSeckillOrderResultRspVO> findSeckillOrderResult(@RequestBody @Validated FindSeckillOrderResultReqVO reqVO) {
        return orderService.findSeckillOrderResult(reqVO);
    }

    @ApiOperationLog(description = "订阅秒杀订单处理结果")
    @PostMapping(value = "/result/subscribe" , produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter subscribeSeckillOrderResult(@RequestBody  @Validated SubscribeSeckillOrderResultReqVO reqVO){
        // 获取当前登录用户 ID
        long userId = StpUtil.getLoginIdAsLong();
        return seckillOrderResultNotifyService.subscribe(userId, reqVO.getOrderNo());
    }

}
