package com.spring.jobPortal.DTO;

import com.spring.jobPortal.Entity.ApplicationStatus;

public class ApplicationStatusDTO
{
    private ApplicationStatus changedStatus;

    public ApplicationStatus getChangedStatus() {
        return changedStatus;
    }

    public void setChangedStatus(ApplicationStatus changedStatus) {
        this.changedStatus = changedStatus;
    }
}
