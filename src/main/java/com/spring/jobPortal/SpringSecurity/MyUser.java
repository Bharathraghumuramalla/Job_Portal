package com.spring.jobPortal.SpringSecurity;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class MyUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String userName;

    @Column(nullable = false)
    private String password;


    @Column(nullable = false)
    private String role;

    public int getId() { return id; }
    public String getUserName() { return userName; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    public void setId(int id) { this.id = id; }
    public void setUserName(String userName) { this.userName = userName; }
    public void setPassword(String password) { this.password = password; }
    public void setRole(String role) { this.role = role; }
}
