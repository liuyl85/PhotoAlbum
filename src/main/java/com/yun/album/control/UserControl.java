package com.yun.album.control;

import com.yun.album.bean.ResultData;
import com.yun.album.bean.User;
import com.yun.album.common.Constant;
import com.yun.album.common.StatusCode;
import com.yun.album.service.IUserService;
import com.yun.album.vo.UserEditVo;
import com.yun.album.vo.UserRegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@RestController
public class UserControl {
    private final Logger logger = LoggerFactory.getLogger(UserControl.class);
    @Resource
    private IUserService userService;

    @PostMapping(value = "/register")
    public ResultData register(@Valid UserRegisterVo vo, BindingResult result) {
        try {
            if(result.hasErrors()){
                return new ResultData(StatusCode.ERROR);
            }

            int registerResult = userService.register(vo);
            return new ResultData(registerResult);
        } catch (Exception e) {
            logger.error("register user error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/reset_pwd", params = {"phone", "pwd", "code"})
    public ResultData resetPwd(String phone, String pwd, String code) {
        try {
            int result = userService.resetPassword(phone, pwd, code);
            return new ResultData(result);
        } catch (Exception e) {
            logger.error("reset user password error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/login", params = {"acc", "pwd"})
    public ResultData login(String acc, String pwd) {
        try {
            Object result = userService.login(acc, pwd);
            if(result instanceof Integer){
                return new ResultData((int)result);
            }

            User user = (User)result;
            Map<String, Object> map = new HashMap<>();
            map.put("name", user.getName());
            map.put("token", user.getToken());
            return new ResultData(StatusCode.SUCCESS, map);
        } catch (Exception e) {
            logger.error("user login error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @GetMapping(value = "/user/refreshToken")
    public ResultData refreshToken(@RequestHeader(Constant.TOKEN_HEADER_NAME) String authorization) {
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

    @PostMapping(value = "/user/edit")
    public ResultData editUser(@Valid UserEditVo vo, BindingResult result) {
        try {
            if(result.hasErrors()){
                return new ResultData(StatusCode.ERROR);
            }

            return null;
        } catch (Exception e) {
            logger.error("user edit message error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/user/set_phone", params = {"phone"})
    public ResultData editPhone(String phone) {
        try {
            return null;
        } catch (Exception e) {
            logger.error("user set phone error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

    @PostMapping(value = "/user/set_pwd", params = {"oldPwd", "newPwd"})
    public ResultData editPwd(@RequestAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER) User user, String oldPwd, String newPwd) {
        try {
            int result = userService.editPassword(user, oldPwd, newPwd);
            return new ResultData(result);
        } catch (Exception e) {
            logger.error("user set password error.", e);
            return new ResultData(StatusCode.ERROR);
        }
    }

}
