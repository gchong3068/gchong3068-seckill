package com.gchong3068.seckill.goods.model.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @Author: gchong3068
 * @Date: 2026/5/22 9:09
 * @Version: v1.0.0
 * @Description: 查询秒杀商品列表入参
 **/

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FindSeckillGoodsListReqVO {

    @NotNull(message = "活动 ID 不能为空")
    @Positive(message = "活动 ID 不合法")
    private Long activityId;




}
