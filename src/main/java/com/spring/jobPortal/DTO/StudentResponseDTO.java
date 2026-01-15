package com.spring.jobPortal.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class StudentResponseDTO
{
    private int studentId;
    private String studentName;
    private String college;
    private Integer age;
    private Integer yearOfPassing;
    private String email;

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

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
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


}
