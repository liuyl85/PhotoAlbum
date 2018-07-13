package com.yun.album.service.impl;

import com.yun.album.bean.User;
import com.yun.album.common.StatusCode;
import com.yun.album.dao.IUserDao;
import com.yun.album.dao.entity.UserEntity;
import com.yun.album.exception.MD5DigestException;
import com.yun.album.security.JwtTokenUtil;
import com.yun.album.security.JwtUser;
import com.yun.album.service.IUserService;
import com.yun.album.util.IdFactory;
import com.yun.album.util.RedisUtils;
import com.yun.album.util.Tools;
import com.yun.album.vo.UserRegisterVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * 用户操作接口实现
 */
@Service
public class UserServiceImpl implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Value("${token.prefix}")
    private String tokenPrefix;
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private PasswordEncoder passwordEncoder;
    @Resource
    private IUserDao userDao;
    @Resource
    private RedisUtils redisUtils;

    @Override
    public int register(UserRegisterVo vo) {
        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return StatusCode.CREATE_ID_ERROR;
        }

        UserEntity user = new UserEntity();
        user.setId(newId);
        user.setAcc(vo.getAcc());
        user.setPhone(vo.getPhone());
        user.setCreateTime(LocalDateTime.now());

        try {
            String pwd = MessageFormat.format("{0}&{1}&{2}", user.getAcc(), vo.getPwd(), user.getAcc());
            pwd = Tools.md5Digest(pwd);
            pwd = passwordEncoder.encode(pwd);
            user.setPwd(pwd);
        } catch (MD5DigestException e) {
            logger.error("password md5 digest error.", e);
            return StatusCode.MD5_DIGEST_FAILED;
        }

        userDao.insert(user);
        return StatusCode.SUCCESS;
    }

    @Override
    public Object login(String username, String password) {
        try {
            password = MessageFormat.format("{0}&{1}&{2}", username, password, username);
            password = Tools.md5Digest(password);
        } catch (MD5DigestException e) {
            logger.error("password md5 digest error.", e);
            return StatusCode.MD5_DIGEST_FAILED;
        }

        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtTokenUtil.generateToken((UserDetails)authentication.getPrincipal());

            User user = ((JwtUser)authentication.getPrincipal()).getUser();
            user.setToken(token);
            redisUtils.set(user.getAcc(), user, jwtTokenUtil.getValidTimeLength());

            return token;
        } catch (BadCredentialsException e) {
            logger.error("account or password error.", e);
            return StatusCode.ACC_OR_PWD_ERROR;
        }
    }

    @Override
    public String refreshToken(String oldToken) {
        if(oldToken.startsWith(tokenPrefix)){
            String token = oldToken.substring(tokenPrefix.length());
            return jwtTokenUtil.isTokenExpired(token) ? null : jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

//    @Override
//    public PageInfo<User> getUserList(int pageNum, int pageSize) {
//        Page<User> page = PageHelper.startPage(pageNum, pageSize);
////        userDao.selectUser(startDate, endDate, name);
//        return new PageInfo<>(page);
//    }
}
