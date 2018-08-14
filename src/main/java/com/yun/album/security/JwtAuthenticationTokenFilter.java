package com.yun.album.security;

import com.yun.album.bean.User;
import com.yun.album.common.Constant;
import com.yun.album.util.RedisUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.MessageFormat;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(Constant.TOKEN_HEADER_NAME);
        if(authHeader != null && authHeader.startsWith(Constant.TOKEN_PREFIX)){
            String authToken = authHeader.substring(Constant.TOKEN_PREFIX.length());
            String userAcc;
            if(authToken.length() > 0 && (userAcc = jwtTokenUtil.getUserAccFromToken(authToken)) != null){
                User user = redisUtils.get(MessageFormat.format(Constant.USER_KEY, userAcc));
                if(user != null && authToken.equals(user.getToken()) && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getAcc(), null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    request.setAttribute(Constant.REQUEST_ATTRIBUTE_NAME_USER, user);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
