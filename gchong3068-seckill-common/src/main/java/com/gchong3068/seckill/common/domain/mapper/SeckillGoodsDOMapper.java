package com.gchong3068.seckill.common.domain.mapper;

import com.gchong3068.seckill.common.domain.dataobject.SeckillGoodsDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SeckillGoodsDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillGoodsDO record);

    int insertSelective(SeckillGoodsDO record);

    SeckillGoodsDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillGoodsDO record);

    int updateByPrimaryKey(SeckillGoodsDO record);

    /**
     * 根据活动Id查询活动下秒杀商品
     * @param activityId
     * @return
     */
    List<SeckillGoodsDO> selectByActivityId(@Param("activityId") Long activityId);


    /**
     * 根据活动 ID 和商品 ID 查询秒杀商品
     *
     * @param activityId
     * @param goodsId
     * @return
     */
    SeckillGoodsDO selectByActivityIdAndGoodsId(@Param("activityId") Long activityId,
                                                @Param("goodsId") Long goodsId);


    /**
     * 扣减秒杀库存
     *
     * @param id 秒杀商品关联表主键 ID
     * @return 影响行数
     */
    int deductStock(@Param("id") Long id);

}