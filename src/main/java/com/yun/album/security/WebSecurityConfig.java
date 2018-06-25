package com.yun.album.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;
    @Resource
    private EntryPointUnauthorizedHandler entryPointUnauthorizedHandler;
    @Resource
    private RestAccessDeniedHandler restAccessDeniedHandler;

    @Bean
    public AuthenticationManager getAuthenticationManager() throws Exception {
        return authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder, @Qualifier("JwtUserDetailsService") UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();
        // 由于使用的是JWT，我们这里不需要csrf
        http.csrf().disable();
        // 基于token，所以不需要session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and();
        http.authorizeRequests()
                .antMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .antMatchers("/user/**").permitAll()
                .anyRequest().authenticated()
                .and();
        // 禁用缓存
        http.headers().cacheControl();
        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling().authenticationEntryPoint(entryPointUnauthorizedHandler).accessDeniedHandler(restAccessDeniedHandler);

    }
}
