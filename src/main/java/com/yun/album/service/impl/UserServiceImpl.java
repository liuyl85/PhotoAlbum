package com.yun.album.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.yun.album.bean.PageInfo;
import com.yun.album.bean.User;
import com.yun.album.common.StatusCode;
import com.yun.album.dao.IUserDao;
import com.yun.album.exception.MD5DigestException;
import com.yun.album.security.JwtTokenUtil;
import com.yun.album.service.IUserService;
import com.yun.album.util.IdFactory;
import com.yun.album.util.Tools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户操作接口实现
 */
@Service
public class UserServiceImpl implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
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
        StringBuilder temp = new StringBuilder();
        temp.append(username).append(" ");
        temp.append(password).append(" ");
        temp.append(username);
        try {
            password = Tools.md5Digest(temp.toString());
        } catch (MD5DigestException e) {
            logger.error("password md5 digest error.", e);
            return String.valueOf(StatusCode.MD5_DIGEST_FAILED);
        }

        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenUtil.generateToken((UserDetails)authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            e.printStackTrace();
            return String.valueOf(StatusCode.ACC_OR_PWD_ERROR);
        }
    }

    @Override
    public int register(User user) {
        if(userDao.selectByAcc(user.getAcc()) != null){
            return StatusCode.USER_ALREADY_EXISTED;
        }

        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return StatusCode.CREATE_ID_ERROR;
        }

        user.setId(newId);

        StringBuilder temp = new StringBuilder();
        temp.append(user.getAcc()).append(" ");
        temp.append(user.getPwd()).append(" ");
        temp.append(user.getAcc());
        try {
            user.setPwd(passwordEncoder.encode(Tools.md5Digest(temp.toString())));
        } catch (MD5DigestException e) {
            logger.error("password md5 digest error.", e);
            return StatusCode.MD5_DIGEST_FAILED;
        }

        userDao.insert(user);
        return StatusCode.SUCCESS;
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        return jwtTokenUtil.isTokenExpired(token) ? null : jwtTokenUtil.refreshToken(token);
    }

    @Override
    public PageInfo<User> getUserList(int pageNum, int pageSize) {
        Page<User> page = PageHelper.startPage(pageNum, pageSize);
//        userDao.selectUser(startDate, endDate, name);
        return new PageInfo<>(page);
    }
}
