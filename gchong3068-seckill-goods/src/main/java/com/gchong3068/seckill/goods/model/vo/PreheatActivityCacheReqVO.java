package com.gchong3068.seckill.goods.model.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Author: gchong3068
 * @Date: 2026/6/11 14:23
 * @Version: v1.0.0
 * @Description: 预热商品缓存
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PreheatActivityCacheReqVO {

    @NotNull(message = "活动Id 不能为空")
    @Positive(message = "活动Id 不合法")
    private Long activityId;

}
