package com.gchong3068.seckill.order.controller;

import com.gchong3068.seckill.common.aspect.ApiOperationLog;
import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.order.model.vo.DoSeckillReqVO;
import com.gchong3068.seckill.order.model.vo.DoSeckillRspVO;
import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultReqVO;
import com.gchong3068.seckill.order.model.vo.FindSeckillOrderResultRspVO;
import com.gchong3068.seckill.order.service.OrderService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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




}
