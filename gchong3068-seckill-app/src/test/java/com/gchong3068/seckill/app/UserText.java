package com.gchong3068.seckill.app;


import com.gchong3068.seckill.common.domain.dataobject.UserDO;
import com.gchong3068.seckill.common.domain.mapper.UserDOMapper;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;

/**
 * @author: gchong3068
 * @date: 2026年04月11日12:54
 * @Version: 1.0
 * @Description:
 */
@SpringBootTest
public class UserText {

    @Resource
    private UserDOMapper userDOMapper;


    /**
     * 添加一条用户记录
     */
    @Test
    void testInsertUser() {
        userDOMapper.insert(UserDO.builder()
                .nickname("犬小哈")
                .password("123456")
                .mobile("18019988888")
                .status(1)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build());
    }

}
