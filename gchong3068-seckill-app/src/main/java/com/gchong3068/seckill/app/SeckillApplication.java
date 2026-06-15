package com.gchong3068.seckill.app;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author: gchong3068
 * @date: 2026年04月11日12:05
 * @Version: 1.0
 * @Description: 启动类
 */
@SpringBootApplication
@ComponentScan({"com.gchong3068.seckill.*"})
@MapperScan("com.gchong3068.seckill.common.domain.mapper")
public class SeckillApplication {

    public static void main(String[] args) {
        SpringApplication.run(SeckillApplication.class, args);
    }

}
