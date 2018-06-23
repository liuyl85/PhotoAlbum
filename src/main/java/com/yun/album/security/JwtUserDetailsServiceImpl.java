package com.yun.album.security;

import com.yun.album.bean.User;
import com.yun.album.dao.IUserDao;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.selectByAcc(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("No user found with user account '%s'.", username));
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            List<String> roles = new ArrayList<>();
            roles.add("ROLE_USER");
            user.setRoles(roles);
            return new JwtUser(user.getAcc(), encoder.encode(user.getPwd()), user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()));
        }
    }
}
