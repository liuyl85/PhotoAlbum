package com.yun.album.service.impl;

import com.yun.album.bean.User;
import com.yun.album.common.StatusCode;
import com.yun.album.dao.IUserDao;
import com.yun.album.security.JwtTokenUtil;
import com.yun.album.service.IUserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户操作接口实现
 */
@Service
public class UserServiceImpl implements IUserService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IUserDao userDao;

    @Override
    public String login(String username, String password) {
        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
        Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken((UserDetails)authentication.getPrincipal());
    }

    @Override
    public int register(User user) {
        String account = user.getAcc();
        if(userDao.selectByAcc(account) != null){
            return StatusCode.USER_ALREADY_EXISTED;
        }

        String rawPassword = user.getPwd();
        user.setPwd(passwordEncoder.encode(rawPassword));

        List<String> roles = new ArrayList<>();
        roles.add("ROLE_USER");
        user.setRoles(roles);

        userDao.insert(user);
        return StatusCode.SUCCESS;
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        return jwtTokenUtil.isTokenExpired(token) ? null : jwtTokenUtil.refreshToken(token);
    }
}
