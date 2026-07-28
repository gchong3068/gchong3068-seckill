package com.gchong3068.seckill.order.model.vo;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gchong3068
 * @date: 2026年07月24日17:56
 * @Version: 1.0
 * @Description:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubscribeSeckillOrderResultReqVO {

    @NotBlank(message = "订单号不能为空")
    private String orderNo;


}
