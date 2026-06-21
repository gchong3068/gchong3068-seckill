package com.gchong3068.seckill.order.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author: gchong3068
 * @date: 2026年06月21日20:25
 * @Version: 1.0
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeckillOrderMqDTO {

    /**
     *下单用户Id
     */
    private Long userId;

    /**
     * 秒杀活动Id
     */
    private Long activityId;

    /**
     * 秒杀商品ID
     */
    private Long goodsId;

    /**
     * 秒杀商品主键Id
     */
    private Long seckillGoodsId;

    /**
     * 秒杀价格
     */
    private BigDecimal seckillPrice;

    /**
     * 订单号
     */
    private String orderNo;

    /**
     * 用户发起请求的时间（可用于追踪消息延迟）
     */
    private LocalDateTime requestTime;



}
