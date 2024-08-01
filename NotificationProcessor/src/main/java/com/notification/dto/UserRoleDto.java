package com.notification.dto;

import java.util.List;

public class UserRoleDto {

    private String role;
    private List<String> rulesKey;

    public String getRole() {
        return role;
    }

    public List<String> getRulesKey() {
        return rulesKey;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setRulesKey(List<String> rulesKey) {
        this.rulesKey = rulesKey;
    }
}
