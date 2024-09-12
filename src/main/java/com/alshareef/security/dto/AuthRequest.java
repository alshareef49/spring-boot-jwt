package com.alshareef.security.dto;

import lombok.Generated;

public class AuthRequest {
    private String username;
    private String password;

    public AuthRequest() {
    }

    @Generated
    public String getUsername() {
        return this.username;
    }

    @Generated
    public String getPassword() {
        return this.password;
    }

    @Generated
    public void setUsername(final String username) {
        this.username = username;
    }

    @Generated
    public void setPassword(final String password) {
        this.password = password;
    }
}
