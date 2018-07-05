package com.yun.album.control;

import com.yun.album.bean.ResultData;
import com.yun.album.common.StatusCode;
import com.yun.album.service.IUserService;
import com.yun.album.vo.UserEditVo;
import com.yun.album.vo.UserRegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;

@RestController
public class UserControl {
    private final Logger logger = LoggerFactory.getLogger(UserControl.class);
    @Resource
    private IUserService userService;

    @PostMapping(value = "/login", params = {"acc", "pwd"})
    public ResultData login(String acc, String pwd) {
        try {
            Object result = userService.login(acc, pwd);
            if(result instanceof Integer){
                return new ResultData((int)result);
            }

            return new ResultData(StatusCode.SUCCESS, result);
        } catch (Exception e) {
            logger.error("user login error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @GetMapping(value = "/refreshToken")
    public ResultData refreshToken(@RequestHeader String authorization) {
        try {
            Object result = userService.refreshToken(authorization);
            if(result == null){
                return new ResultData(StatusCode.REFRESH_TOKEN_FAILED);
            }
            return new ResultData(StatusCode.SUCCESS, result);
        } catch (Exception e) {
            logger.error("refresh token error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/register")
    public ResultData register(@Valid UserRegisterVo vo) {
        try {
            int result = userService.register(vo);
            return new ResultData(result);
        } catch (Exception e) {
            logger.error("register user error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/user/edit")
    public ResultData editUser(@Valid UserEditVo vo) {
        return null;
    }

    @PostMapping(value = "/user/pwd")
    public ResultData editPwd(String pwd) {
        return null;
    }

}
