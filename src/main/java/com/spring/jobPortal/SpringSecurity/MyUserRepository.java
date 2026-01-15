package com.spring.jobPortal.SpringSecurity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Integer>
{
    Optional<MyUser> findByUserName(String userName);

    //MyUser findByUserName(String userName);

    boolean existsByUserName(String username);
}

