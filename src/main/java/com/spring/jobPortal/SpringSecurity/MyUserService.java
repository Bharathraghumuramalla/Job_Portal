package com.spring.jobPortal.SpringSecurity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
public class MyUserService implements UserDetailsService {

    @Autowired
    private MyUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException
    {
        MyUser user = repo.findByUserName(username).orElseThrow(() -> new UsernameNotFoundException("User not found"));


        return User.withUsername(user.getUserName())
                .password(user.getPassword())
                .roles(user.getRole().replace("ROLE_", ""))
                .build();

    }

}

