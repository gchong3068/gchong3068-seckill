package com.gchong3068.seckill.common.domain.mapper;

import com.gchong3068.seckill.common.domain.dataobject.SeckillOrderDO;

public interface SeckillOrderDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(SeckillOrderDO record);

    int insertSelective(SeckillOrderDO record);

    SeckillOrderDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(SeckillOrderDO record);

    int updateByPrimaryKey(SeckillOrderDO record);





}