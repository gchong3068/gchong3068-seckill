package com.gchong3068.seckill.common.domain.mapper;

import com.gchong3068.seckill.common.domain.dataobject.UserDO;

public interface UserDOMapper {
    int deleteByPrimaryKey(Long id);

    int insert(UserDO record);

    int insertSelective(UserDO record);

    UserDO selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(UserDO record);

    int updateByPrimaryKey(UserDO record);


    /*
     * 根据手机号查询用户 ID
     * @author gchong3068
     * @date 2026/4/11 22:19
     * @param mobile
     * @return java.lang.Long
     */
    Long selectIdByMobile(String mobile);


    /*
     * 根据手机号查询用户信息
     * @author gchong3068
     * @date 2026/4/14 21:57
     * @param mobile
     * @return com.gchong3068.seckill.common.domain.dataobject.UserDO
     */
    UserDO selectByMobile(String mobile);

}