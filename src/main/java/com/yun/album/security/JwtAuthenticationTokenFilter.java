package com.yun.album.security;

import com.yun.album.bean.User;
import com.yun.album.util.RedisUtils;
import org.springframework.beans.factory.annotation.Value;
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

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {
    @Value("${token.header-name}")
    private String headerName;
    @Value("${token.prefix}")
    private String tokenPrefix;
    @Resource
    private JwtTokenUtil jwtTokenUtil;
    @Resource
    private RedisUtils redisUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(headerName);
        if(authHeader != null && authHeader.startsWith(tokenPrefix)){
            String authToken = authHeader.substring(tokenPrefix.length());
            String userAcc;
            if(authToken.length() > 0 && (userAcc = jwtTokenUtil.getUserAccFromToken(authToken)) != null){
                User user = redisUtils.get(userAcc);
                if(user != null && authToken.equals(user.getToken()) && SecurityContextHolder.getContext().getAuthentication() == null){
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user.getAcc(), null, null);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
