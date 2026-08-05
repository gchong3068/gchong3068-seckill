package com.gchong3068.seckill.order.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gchong3068
 * @date: 2026年08月05日11:23
 * @Version: 1.0
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum SeckillStockDeductResultEnum {


    REPEATED_ORDER(2L, "请勿重复参与秒杀"),
    SUCCESS(1L,"库存预扣成功"),
    SOLD_OUT(0L,"秒杀商品已售罄"),
    STOCK_NOT_PREHEATED(-1L,"秒杀商品库存未预热");



    private final Long code;

    private final String description;


    public static SeckillStockDeductResultEnum getByCode(Long code){
        for(SeckillStockDeductResultEnum value : values()){
            if (value.getCode().equals(code)){
                return value;
            }
        }
        return  null;
    }



}
