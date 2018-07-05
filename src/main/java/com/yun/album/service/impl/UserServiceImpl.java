package com.yun.album.service.impl;

import com.yun.album.common.StatusCode;
import com.yun.album.dao.IUserDao;
import com.yun.album.dao.entity.UserEntity;
import com.yun.album.exception.MD5DigestException;
import com.yun.album.security.JwtTokenUtil;
import com.yun.album.service.IUserService;
import com.yun.album.util.IdFactory;
import com.yun.album.util.Tools;
import com.yun.album.vo.UserRegisterVo;
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
import java.time.LocalDateTime;

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
    public int register(UserRegisterVo vo) {
        Long newId = IdFactory.instance.createNewId();
        if(newId == null){
            return StatusCode.CREATE_ID_ERROR;
        }

        UserEntity user = new UserEntity();
        user.setId(newId);
        user.setAcc(vo.getAcc());
        user.setPhone(vo.getPhone());
        user.setName(vo.getName());
        user.setSex(vo.getSex());
        user.setCreateTime(LocalDateTime.now());

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

        try {
            userDao.insert(user);
            return StatusCode.SUCCESS;
        } catch (Exception e) {
            logger.error("insert user error.", e);
            return StatusCode.ERROR;
        }

    }

    @Override
    public Object login(String username, String password) {
        StringBuilder temp = new StringBuilder();
        temp.append(username).append(" ");
        temp.append(password).append(" ");
        temp.append(username);
        try {
            password = Tools.md5Digest(temp.toString());
        } catch (MD5DigestException e) {
            logger.error("password md5 digest error.", e);
            return StatusCode.MD5_DIGEST_FAILED;
        }

        try {
            UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(upToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            return jwtTokenUtil.generateToken((UserDetails)authentication.getPrincipal());
        } catch (BadCredentialsException e) {
            logger.error("account or password error.", e);
            return StatusCode.ACC_OR_PWD_ERROR;
        }
    }

    @Override
    public String refreshToken(String oldToken) {
        String token = oldToken.substring("Bearer ".length());
        return jwtTokenUtil.isTokenExpired(token) ? null : jwtTokenUtil.refreshToken(token);
    }

//    @Override
//    public PageInfo<User> getUserList(int pageNum, int pageSize) {
//        Page<User> page = PageHelper.startPage(pageNum, pageSize);
////        userDao.selectUser(startDate, endDate, name);
//        return new PageInfo<>(page);
//    }
}
