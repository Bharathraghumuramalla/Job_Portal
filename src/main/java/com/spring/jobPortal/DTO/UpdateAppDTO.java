package com.spring.jobPortal.DTO;

import com.spring.jobPortal.Entity.ApplicationStatus;

public class UpdateAppDTO
{

    private ApplicationStatus finalResult;

    public ApplicationStatus getFinalResult() {
        return finalResult;
    }
    public void setFinalResult(ApplicationStatus finalResult) {
        this.finalResult = finalResult;
    }
}
