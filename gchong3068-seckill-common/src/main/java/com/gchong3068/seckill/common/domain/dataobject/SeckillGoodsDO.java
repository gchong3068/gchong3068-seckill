package com.gchong3068.seckill.common.domain.dataobject;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Date;
/**
 * @Author: gchong3068
 * @Date: 2026/5/22 08:55
 * @Version: v1.0.0
 * @Description: 秒杀商品关联表 DO
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SeckillGoodsDO {
    private Long id;

    private Long activityId;

    private Long goodsId;

    private String seckillTitle;

    private String seckillImg;

    private BigDecimal seckillPrice;

    private Integer seckillTotal;

    private Integer seckillStock;

    private Integer seckillLimit;

    private Integer sort;

    private Integer version;

    private Integer isDeleted;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;


}