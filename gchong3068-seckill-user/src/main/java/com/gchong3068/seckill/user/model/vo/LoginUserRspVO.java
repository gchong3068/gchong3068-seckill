package com.gchong3068.seckill.user.model.vo;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: gchong3068
 * @date: 2026年04月14日21:55
 * @Version: 1.0
 * @Description: 用户登录
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginUserRspVO {


    /**
     * Token 令牌
     */
    private String token;


    /**
     * 用户信息
     */
    private UserInfo userInfo;

    /**
     * 用户信息内部类
     */
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    @Builder
    public static class UserInfo {

        /**
         * 用户 ID
         */
        private Long id;

        /**
         * 昵称
         */
        private String nickname;

        /**
         * 头像
         */
        private String avatar;
    }

}
