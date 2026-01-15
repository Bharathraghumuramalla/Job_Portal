package com.spring.jobPortal.SpringSecurity;

public class EmployeeRegisterDTO
{
    private String employeeName;
    private int age;
    private String username;
    private String password;

    public String getEmployeeName() {
        return employeeName;
    }

    public int getAge() {
        return age;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
