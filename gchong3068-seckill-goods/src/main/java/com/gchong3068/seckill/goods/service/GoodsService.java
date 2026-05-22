package com.gchong3068.seckill.goods.service;

import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.goods.model.vo.FindSeckillGoodsDetailReqVO;
import com.gchong3068.seckill.goods.model.vo.FindSeckillGoodsDetailRspVO;
import com.gchong3068.seckill.goods.model.vo.FindSeckillGoodsListReqVO;
import com.gchong3068.seckill.goods.model.vo.FindSeckillGoodsListRspVO;

import java.util.List;

/**
 * @Author: gchong3068
 * @Date: 2026/5/22 9:23
 * @Version: v1.0.0
 * @Description: 商品模块业务
 **/
public interface GoodsService {


    /**
     * 查询秒杀商品列表
     * @param reqVO
     * @return
     */
    Response<List<FindSeckillGoodsListRspVO>>  findSeckillGoodsList(FindSeckillGoodsListReqVO reqVO);


    /**
     * 查询秒杀商品详情
     * @param reqVO
     * @return
     */
    Response<FindSeckillGoodsDetailRspVO> findSeckillGoodsDetail(FindSeckillGoodsDetailReqVO reqVO);

}
