package com.gchong3068.seckill.common.aspect;


import java.lang.annotation.*;

/**
 * @author: gchong3068
 * @date: 2026年04月11日21:31
 * @Version: 1.0
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
@Documented
public @interface ApiOperationLog {

    /*
     * API 功能描述
     * @author gchong3068
     * @date 2026/4/11 21:32
     * @return java.lang.String
     */
    String description() default "";

}
