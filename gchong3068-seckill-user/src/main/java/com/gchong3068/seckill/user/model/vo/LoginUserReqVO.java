package com.gchong3068.seckill.user.model.vo;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gchong3068
 * @date: 2026年04月14日21:51
 * @Version: 1.0
 * @Description: 用户登录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserReqVO {

    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;



    @NotNull(message = "登录类型不能为空")
    private Integer type;

    //密码
    private String password;

    //短信验证码
    private String verifyCode;

}
