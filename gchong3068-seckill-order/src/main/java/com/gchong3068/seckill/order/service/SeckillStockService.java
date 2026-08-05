package com.gchong3068.seckill.order.service;


import com.gchong3068.seckill.order.enums.SeckillStockDeductResultEnum;

/**
 * @author: gchong3068
 * @date: 2026年08月05日11:27
 * @Version: 1.0
 * @Description:
 */
public interface SeckillStockService {


    /**
     * Redis Lua 原子预扣库存
     * @param activityId
     * @param goodsId
     * @return
     */
    SeckillStockDeductResultEnum preDeductStock(Long activityId, Long goodsId, Long userId, Long userOrderTtlSeconds);






}
