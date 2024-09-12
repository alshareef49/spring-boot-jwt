package com.alshareef.security.dto;

import lombok.Generated;

public class AuthResponse {
    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }

    @Generated
    public String getToken() {
        return this.token;
    }

    @Generated
    public void setToken(final String token) {
        this.token = token;
    }
}
