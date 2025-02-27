package com.lam.Service;

import com.lam.mapper.ManageMapper;
import com.lam.pojo.Manager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AAUserDetailService implements UserDetailsService {
    @Autowired
    private ManageMapper manageMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager user = manageMapper.findUserPhone(username);
        System.out.println(user);
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("该用户不存在");
        }
        //默认角色为空
        String[] roleArr = null;
        return User.withUsername(user.getPhone()).password(user.getM_pwd()).roles(user.getRole()).build();
    }
}
