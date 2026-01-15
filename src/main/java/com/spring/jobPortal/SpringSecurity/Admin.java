package com.spring.jobPortal.SpringSecurity;


import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Admin {

    @Autowired
    private MyUserRepository repo;

    @Autowired
    private PasswordEncoder encoder;

    private final String adminUserName = "admin";
    private final String adminPassWord = "admin123";

    @PostConstruct
    public void createAdmin() {

        if(repo.existsByUserName(adminUserName))
        {
            return;
        }

        MyUser admin = new MyUser();
        admin.setUserName(adminUserName);
        admin.setPassword(encoder.encode(adminPassWord));
        admin.setRole("ROLE_ADMIN");

        repo.save(admin);
    }
}
