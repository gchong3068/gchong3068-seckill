package com.gchong3068.seckill.user.service;


import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.user.model.vo.LoginUserReqVO;
import com.gchong3068.seckill.user.model.vo.LoginUserRspVO;
import com.gchong3068.seckill.user.model.vo.RegisterUserReqVO;
import com.gchong3068.seckill.user.model.vo.SendVerifyCodeReqVO;

/**
 * @author: gchong3068
 * @date: 2026年04月11日22:22
 * @Version: 1.0
 * @Description: 用户业务
 */
public interface UserService {


    /*
     * 用户注册
     * @author gchong3068
     * @date 2026/4/11 22:22
     * @param registerUserReqVO
     * @return com.gchong3068.seckill.common.utils.Response<?>
     */
    Response<?> register(RegisterUserReqVO registerUserReqVO);

    /*
     * 用户登录
     * @author gchong3068
     * @date 2026/4/14 21:59
     * @param loginUserReqVO
     * @return com.gchong3068.seckill.common.utils.Response<com.gchong3068.seckill.user.model.vo.LoginUserRspVO>
     */
    Response<LoginUserRspVO> login(LoginUserReqVO loginUserReqVO);

    /**
     * 发送验证码
     * @param sendVerifyCodeReqVO
     * @return
     */
    Response<?> sendVerifyCode(SendVerifyCodeReqVO sendVerifyCodeReqVO);
}
