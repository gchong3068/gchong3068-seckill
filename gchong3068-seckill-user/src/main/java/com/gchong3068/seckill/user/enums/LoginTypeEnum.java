package com.gchong3068.seckill.user.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gchong3068
 * @date: 2026年04月14日21:49
 * @Version: 1.0
 * @Description: 登录类型枚举
 */
@Getter
@AllArgsConstructor
public enum LoginTypeEnum {


    PASSWORD(1,"密码登录"),
    VERIFY(2,"验证码登录"),
    ;


    private final Integer code;

    private final String description;

}
