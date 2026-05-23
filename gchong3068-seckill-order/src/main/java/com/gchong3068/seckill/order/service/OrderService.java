package com.gchong3068.seckill.order.service;

import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.order.model.vo.DoSeckillReqVO;
import com.gchong3068.seckill.order.model.vo.DoSeckillRspVO;

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

}
