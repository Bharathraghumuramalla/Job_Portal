package com.spring.jobPortal.DTO;

public class EmployeeResponseDTO
{
    private int employeeId;
    private String employeeName;
    private int age;

    public int getEmployeeId() {
        return employeeId;
    }
    public int getAge() {
        return age;
    }
    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
