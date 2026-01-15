package com.spring.jobPortal.DTO;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class JobCreateDTO
{
    private String role;
    private String location;
    private int experience;

    public String getRole() {
        return role;
    }

    public String getLocation() {
        return location;
    }

    public int getExperience() {
        return experience;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setExperience(int experience) {
        this.experience = experience;
    }
}
