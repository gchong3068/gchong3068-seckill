package com.gchong3068.seckill.user.controller;


import com.gchong3068.seckill.common.aspect.ApiOperationLog;
import com.gchong3068.seckill.common.utils.Response;
import com.gchong3068.seckill.user.model.vo.RegisterUserReqVO;
import com.gchong3068.seckill.user.service.UserService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author: gchong3068
 * @date: 2026年04月11日22:26
 * @Version: 1.0
 * @Description:
 */
@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {


    @Resource
    private UserService userService;


    /*
     * 用户注册
     * @author gchong3068
     * @date 2026/4/11 22:28
     * @param registerUserReqVO
     * @return com.gchong3068.seckill.common.utils.Response<?>
     */
    @PostMapping("/register")
    @ApiOperationLog(description = "用户注册")
    public Response<?> register(@Validated @RequestBody RegisterUserReqVO registerUserReqVO) {
        return userService.register(registerUserReqVO);
    }



}
