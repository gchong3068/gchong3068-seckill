package com.gchong3068.seckill.common.constant;

/**
 * @Author: gchong3068
 * @Date: 2026/6/5 12:02
 * @Version: v1.0.0
 * @Description: Redis 缓存Key常量
 **/
public class RedisKeyConstants {


    /**
     * 商品列表Key前缀
     * seckill:goods:list:{activityId}
     */
    public static final String GOODS_LIST_PREFIX = "seckill:goods:list:";


    /**
     * 商品缓存过期时间（分钟）
     */
    public static final long GOODS_LIST_TTL_MINUTES = 30;



    /**
     * 商品详情缓存 Key 前缀
     * 完整格式：seckill:goods:detail:{activityId}:{goodsId}
     */
    public static final String GOODS_DETAIL_PREFIX = "seckill:goods:detail:";

    /**
     * 商品详情缓存过期时间（单位：分钟）
     */
    public static final long GOODS_DETAIL_TTL_MINUTES = 30;


}
