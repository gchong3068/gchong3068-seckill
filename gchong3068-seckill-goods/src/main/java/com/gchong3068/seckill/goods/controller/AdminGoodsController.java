package com.gchong3068.seckill.goods.controller;

import com.gchong3068.seckill.common.aspect.ApiOperationLog;
import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.goods.model.vo.PreheatActivityCacheReqVO;
import com.gchong3068.seckill.goods.service.GoodsService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @Author: gchong3068
 * @Date: 2026/6/11 14:25
 * @Version: v1.0.0
 * @Description: TODO
 **/
@RestController
@RequestMapping("/admin/seckill/goods")
@Slf4j
public class AdminGoodsController {

    @Resource
    private GoodsService goodsService;


    @PostMapping("/cache/preheat")
    @ApiOperationLog(description = "手动预热商品缓存")
    public Response<?> preheatCache(@RequestBody  @Validated PreheatActivityCacheReqVO reqVO){
        return goodsService.preheatActivityGoods(reqVO.getActivityId());

    }

}
