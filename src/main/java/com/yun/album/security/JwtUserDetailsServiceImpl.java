package com.yun.album.security;

import com.yun.album.bean.User;
import com.yun.album.dao.IUserDao;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service(value = "JwtUserDetailsService")
public class JwtUserDetailsServiceImpl implements UserDetailsService {
    @Resource
    private IUserDao userDao;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userDao.selectByAcc(username);
        if (user == null) {
            throw new UsernameNotFoundException("user not found.");
        } else {
//            List<String> roles = new ArrayList<>();
//            roles.add("ROLE_USER");
//            user.setRoles(roles);
            //user.getRoles().stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())
            return new JwtUser(user.getAcc(), user.getPwd(), null);
        }
    }
}
