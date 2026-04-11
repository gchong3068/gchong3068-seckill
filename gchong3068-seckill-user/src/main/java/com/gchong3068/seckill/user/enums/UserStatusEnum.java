package com.gchong3068.seckill.user.enums;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author: gchong3068
 * @date: 2026年04月11日22:20
 * @Version: 1.0
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum UserStatusEnum {

    DISABLED(0, "禁用"),
    ENABLED(1, "启用"),
    ;

    private final Integer code;
    private final String description;

}
