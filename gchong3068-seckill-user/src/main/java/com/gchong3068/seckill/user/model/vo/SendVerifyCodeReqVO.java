package com.gchong3068.seckill.user.model.vo;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.logging.log4j.core.config.plugins.validation.constraints.NotBlank;

/**
 * @Author: gchong3068
 * @Date: 2026/5/8 15:39
 * @Version: v1.0.0
 * @Description: 发送验证码
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SendVerifyCodeReqVO {



    @NotBlank(message = "手机号不能为空")
    @Pattern(regexp = "^1[3-9]\\d{9}$", message = "手机号格式不正确")
    private String mobile;


    @NotNull(message = "验证码类型不能为空")
    private Integer type;
}
