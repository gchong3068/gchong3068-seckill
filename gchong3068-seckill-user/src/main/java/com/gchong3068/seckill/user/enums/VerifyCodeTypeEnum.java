package com.gchong3068.seckill.user.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Author: gchong3068
 * @Date: 2026/5/8 15:41
 * @Version: v1.0.0
 * @Description: 验证码连接枚举类
 **/
@Getter
@AllArgsConstructor
public enum VerifyCodeTypeEnum {

    REGISTER(1,"register","注册"),
    LOGIN(2,"login","登录"),
    ;


    private final Integer code;

    private final String purpose;

    private final String description;


    /**
     * 根据 code 获取枚举
     *
     * @param code
     * @return
     */
    public static VerifyCodeTypeEnum valueOf(Integer code){
        for(VerifyCodeTypeEnum typeEnum : values()){
            if (typeEnum.getCode().equals(code)){
                return typeEnum;
            }
        }
        return null;
    }
}
