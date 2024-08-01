package com.notification.dto;

import java.util.List;

public class StepsConfig {

    private String role;
    private List<String> steps;

    public String getRole() {
        return role;
    }

    public List<String> getSteps() {
        return steps;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setSteps(List<String> steps) {
        this.steps = steps;
    }
}
