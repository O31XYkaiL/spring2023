package com.webagregator.webagregator.domain;

public enum ProjectRole {
    TEAM_LEAD("TeamLead"),
    BACKEND("Backend");
    // другие роли

    private final String role;

    ProjectRole(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}
