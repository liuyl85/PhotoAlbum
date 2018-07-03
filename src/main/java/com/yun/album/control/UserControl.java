package com.yun.album.control;

import com.yun.album.service.IUserService;
import com.yun.album.vo.UserVo;
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
    public String register(UserVo vo) {
        int res = userService.register(vo);
        return "result:" + res;
    }

    @GetMapping(value = "/refreshToken")
    public String refreshToken(@RequestHeader String authorization) {
        String res = userService.refreshToken(authorization);
        return res;
    }
}
