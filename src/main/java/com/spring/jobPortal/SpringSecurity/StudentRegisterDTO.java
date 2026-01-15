package com.spring.jobPortal.SpringSecurity;

public class StudentRegisterDTO
{
    private String username;
    private String password;
    private String studentName;
    private String college;
    private int age;
    private int yearOfPassing;
    private String email;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getStudentName() {
        return studentName;
    }

    public String getCollege() {
        return college;
    }

    public int getAge() {
        return age;
    }

    public int getYearOfPassing() {
        return yearOfPassing;
    }

    public String getEmail() {
        return email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setYearOfPassing(int yearOfPassing) {
        this.yearOfPassing = yearOfPassing;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
