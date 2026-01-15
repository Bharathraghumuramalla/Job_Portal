package com.spring.jobPortal.DTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor

public class ApplicationGetDTO
{
    private int studentId;
    private int applicationId;
    private int jobId;
    private String currentStatus;
    private LocalDate appliedDate;
    private String finalResult;

    public String getFinalResult() {
        return finalResult;
    }
    public int getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(int applicationId) {
        this.applicationId = applicationId;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }



    public int getStudentId() {
        return studentId;
    }

    public int getJobId() {
        return jobId;
    }

    public LocalDate getAppliedDate() {
        return appliedDate;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public void setAppliedDate(LocalDate appliedDate) {
        this.appliedDate = appliedDate;
    }
}
