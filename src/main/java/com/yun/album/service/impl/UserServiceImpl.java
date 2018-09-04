package com.yun.album.service.impl;

import com.yun.album.bean.User;
import com.yun.album.bean.UserAccount;
import com.yun.album.bean.ValidateCode;
import com.yun.album.common.Constant;
import com.yun.album.common.StatusCode;
import com.yun.album.common.properties.SystemProperties;
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
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.MessageFormat;
import java.time.LocalDateTime;

/**
 * 用户操作接口实现
 */
@Service
public class UserServiceImpl implements IUserService {
    private final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Resource
    private SystemProperties systemProperties;
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
    public int getValidationCode(String phone) {
        String codeKey = MessageFormat.format(Constant.USER_CODE_KEY, phone);
        ValidateCode validateCode = redisUtils.get(codeKey);
        if(validateCode != null && validateCode.isCding()){
            return StatusCode.VALIDATE_CODE_IS_CDING;
        }

        String code = Tools.getRandomNum(6);
        validateCode = new ValidateCode(phone, code, LocalDateTime.now(), systemProperties);
        redisUtils.set(codeKey, validateCode, systemProperties.getValidateTimeLength());

        //TODO 发送验证码
        return StatusCode.SUCCESS;
    }

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

        int insertNum = userDao.insert(user);
        return insertNum == 1 ? StatusCode.SUCCESS : StatusCode.ACC_ALREADY_EXISTED;
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
            redisUtils.set(MessageFormat.format(Constant.USER_KEY, user.getAcc()), user, systemProperties.getTokenValidTimeLength() * 60 * 60);
            return user;
        } catch (BadCredentialsException e) {
            logger.error("account or password error.", e);
            return StatusCode.ACC_OR_PWD_ERROR;
        }
    }

    @Override
    public String refreshToken(String oldToken) {
        if(oldToken.startsWith(Constant.TOKEN_PREFIX)){
            String token = oldToken.substring(Constant.TOKEN_PREFIX.length());
            String userAcc = jwtTokenUtil.getUserAccFromToken(token);
            User user = redisUtils.get(MessageFormat.format(Constant.USER_KEY, userAcc));
            if(user == null){
                return null;
            }

            token = jwtTokenUtil.refreshToken(token);
            user.setToken(token);
            redisUtils.set(MessageFormat.format(Constant.USER_KEY, user.getAcc()), user, systemProperties.getTokenValidTimeLength() * 60 * 60);
            return token;
        }
        return null;
    }

    @Override
    @Transactional
    public int editPassword(User user, String oldPwd, String newPwd) {
        oldPwd = MessageFormat.format("{0}&{1}&{2}", user.getAcc(), oldPwd, user.getAcc());
        newPwd = MessageFormat.format("{0}&{1}&{2}", user.getAcc(), newPwd, user.getAcc());

        try {
            oldPwd = Tools.md5Digest(oldPwd);
            newPwd = Tools.md5Digest(newPwd);
        } catch (MD5DigestException e) {
            logger.error("password md5 digest error.", e);
            return StatusCode.MD5_DIGEST_FAILED;
        }

        if(oldPwd.equals(newPwd)){
            return StatusCode.TWO_PWD_IS_SAME;
        }

        oldPwd = passwordEncoder.encode(oldPwd);
        if(!passwordEncoder.matches(oldPwd, user.getPwd())){
            return StatusCode.PWD_IS_ERROR;
        }

        newPwd = passwordEncoder.encode(newPwd);
        int updateResult = userDao.updateById(user.getId(), newPwd);
        if(updateResult == 1){
            user.setPwd(newPwd);

            redisUtils.set(MessageFormat.format(Constant.USER_KEY, user.getAcc()), user, systemProperties.getTokenValidTimeLength() * 60 * 60);
        }
        return updateResult == 1 ? StatusCode.SUCCESS : StatusCode.ERROR;
    }

    @Override
    public int resetPassword(String phone, String pwd, String code) {
        String codeKey = MessageFormat.format(Constant.USER_CODE_KEY, phone);
        ValidateCode validateCode = redisUtils.get(codeKey);
        redisUtils.delete(codeKey);
        if(validateCode == null || validateCode.isExpire()){
            return StatusCode.VALIDATE_CODE_IS_EXPIRE;
        }

        if(!validateCode.getCode().equals(code)){
            return StatusCode.VALIDATE_CODE_ERROR;
        }

        UserAccount userAcc = userDao.selectAccByPhone(phone);
        if(userAcc == null){
            return StatusCode.ACCOUNT_IS_NOT_FIND;
        }

        pwd = MessageFormat.format("{0}&{1}&{2}", userAcc.getAcc(), pwd, userAcc.getAcc());
        try {
            pwd = Tools.md5Digest(pwd);
        } catch (MD5DigestException e) {
            logger.error("password md5 digest error.", e);
            return StatusCode.MD5_DIGEST_FAILED;
        }

        pwd = passwordEncoder.encode(pwd);
        int updateResult = userDao.updateById(userAcc.getId(), pwd);
        return updateResult == 1 ? StatusCode.SUCCESS : StatusCode.ERROR;
    }

}
