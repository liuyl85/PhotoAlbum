package com.yun.album.control;

import com.yun.album.bean.User;
import com.yun.album.service.IUserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "user")
public class UserControl {
    @Resource
    private IUserService userService;

    @PostMapping(value = "/login", params = {"acc", "pwd"})
    public String login(String acc, String pwd) {
        try {
            String res = userService.login(acc, pwd);
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }
    }

    @PostMapping(value = "/register")
    public String register(User user) {
        int res = userService.register(user);
        return "result:" + res;
    }

    @GetMapping(value = "/refreshToken")
    public String refreshToken(@RequestHeader String authorization) {
        String res = userService.refreshToken(authorization);
        return res;
    }
}
