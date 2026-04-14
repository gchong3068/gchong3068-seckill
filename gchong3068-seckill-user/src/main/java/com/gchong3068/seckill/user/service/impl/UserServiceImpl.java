package com.gchong3068.seckill.user.service.impl;


import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.gchong3068.seckill.common.domain.dataobject.UserDO;
import com.gchong3068.seckill.common.domain.mapper.UserDOMapper;
import com.gchong3068.seckill.common.enums.ResponseCodeEnum;
import com.gchong3068.seckill.common.exception.BizException;
import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.user.enums.LoginTypeEnum;
import com.gchong3068.seckill.user.enums.UserStatusEnum;
import com.gchong3068.seckill.user.model.vo.LoginUserReqVO;
import com.gchong3068.seckill.user.model.vo.LoginUserRspVO;
import com.gchong3068.seckill.user.model.vo.RegisterUserReqVO;
import com.gchong3068.seckill.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author: gchong3068
 * @date: 2026年04月11日22:23
 * @Version: 1.0
 * @Description: 用户业务实现
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {


    @Resource
    private UserDOMapper userDOMapper;


    /*
     * 用户注册
     * @author gchong3068
     * @date 2026/4/11 22:24
     * @param registerUserReqVO
     * @return com.gchong3068.seckill.common.utils.Response<?>
     */
    @Override
    public Response<?> register(RegisterUserReqVO registerUserReqVO) {
        String mobile = registerUserReqVO.getMobile();
        String password = registerUserReqVO.getPassword();
        String verifyCode = registerUserReqVO.getVerifyCode();


        // 1. 校验验证码
        // TODO: 验证码先写死 123456，后续开发验证码发送接口，再重构这里
        if (!"123456".equals(verifyCode)) {
            throw new BizException(ResponseCodeEnum.USER_VERIFY_CODE_ERROR);
        }

        // 2. 校验手机号是否已注册
        Long existUserId = userDOMapper.selectIdByMobile(mobile);
        if (Objects.nonNull(existUserId)) {
            throw new BizException(ResponseCodeEnum.USER_MOBILE_EXISTS);
        }

        // 3. 密码加密（使用 BCrypt 算法）
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(password);

        // 4. 构建用户实体，插入数据库
        UserDO userDO = UserDO.builder()
                .mobile(mobile)
                .password(encodedPassword)
                .nickname(generateNickname()) // 生成随机用户昵称
                .status(UserStatusEnum.ENABLED.getCode()) // 默认启用
                .build();

        userDOMapper.insertSelective(userDO);

        log.info("==> 用户注册成功, mobile: {}", mobile);

        return Response.success();

    }

    /*
     * 用户登录
     * @author gchong3068
     * @date 2026/4/14 22:00
     * @param loginUserReqVO
     * @return com.gchong3068.seckill.common.utils.Response<com.gchong3068.seckill.user.model.vo.LoginUserRspVO>
     */
    @Override
    public Response<LoginUserRspVO> login(LoginUserReqVO loginUserReqVO) {
        String mobile = loginUserReqVO.getMobile();
        Integer type = loginUserReqVO.getType();

        //1.根据手机号查询用户
        UserDO userDO = userDOMapper.selectByMobile(mobile);

        // 2. 判断用户是否存在
        if (Objects.isNull(userDO)){
            throw new BizException(ResponseCodeEnum.USER_MOBILE_NOT_REGISTERED);
        }

        // 3. 根据登录类型，进行身份验证
        if (Objects.equals(type, LoginTypeEnum.PASSWORD.getCode())) {
            // 密码登录：校验密码是否正确
            checkPassword(loginUserReqVO.getPassword(), userDO.getPassword());
        } else {
            // 验证码登录：校验验证码是否正确
            checkVerifyCode(loginUserReqVO.getVerifyCode());
        }

        // 4. 校验用户状态（是否被禁用）
        if (Objects.equals(userDO.getStatus(), UserStatusEnum.DISABLED.getCode())) {
            throw new BizException(ResponseCodeEnum.USER_STATUS_DISABLED);
        }

        // 5. 调用 SaToken 执行登录，传入用户 ID
        StpUtil.login(userDO.getId());

        // 6. 获取 SaToken 生成的 Token
        String token = StpUtil.getTokenValue();

        // 7. 构建返参对象
        LoginUserRspVO loginUserRspVO = LoginUserRspVO.builder()
                .token(token)
                .userInfo(LoginUserRspVO.UserInfo.builder()
                        .id(userDO.getId())
                        .nickname(userDO.getNickname())
                        .avatar(userDO.getAvatar())
                        .build())
                .build();

        log.info("==> 用户登录成功, userId: {}, mobile: {}", userDO.getId(), mobile);

        return Response.success(loginUserRspVO);

    }


    /*
     * 生成随机昵称
     * @author gchong3068
     * @date 2026/4/11 22:25
     * @return java.lang.String
     */
    private String generateNickname(){
        return "用户" + RandomUtil.randomNumbers(6);
    }


    /**
     * 校验密码
     *
     * @param rawPassword     明文密码
     * @param encodedPassword 加密后的密码
     */
    private void checkPassword(String rawPassword, String encodedPassword) {
        // 密码不能为空
        if (StrUtil.isBlank(rawPassword)) {
            throw new BizException(ResponseCodeEnum.USER_PASSWORD_ERROR);
        }

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        // 使用 BCrypt 校验明文密码和密文密码是否匹配
        boolean matches = passwordEncoder.matches(rawPassword, encodedPassword);
        if (!matches) {
            throw new BizException(ResponseCodeEnum.USER_PASSWORD_ERROR);
        }
    }

    /**
     * 校验验证码
     *
     * @param verifyCode 验证码
     */
    private void checkVerifyCode(String verifyCode) {
        // 验证码不能为空
        if (StrUtil.isBlank(verifyCode)) {
            throw new BizException(ResponseCodeEnum.USER_VERIFY_CODE_ERROR);
        }

        // TODO: 验证码先写死 123456，后续开发验证码发送接口，再重构这里
        if (!"123456".equals(verifyCode)) {
            throw new BizException(ResponseCodeEnum.USER_VERIFY_CODE_ERROR);
        }
    }





}
