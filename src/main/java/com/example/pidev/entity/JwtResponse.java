package com.example.pidev.entity;

public class JwtResponse {

    private User user;
    private String jwtToken;
    private String refreshToken;

    public JwtResponse(User user, String jwtToken, String refreshToken) {
        this.user = user;
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public JwtResponse(String accessToken) {
        this.jwtToken = accessToken;
    }
    public JwtResponse(User user, String jwtToken) {
        this.user = user;
        this.jwtToken = jwtToken;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getJwtToken() {
        return jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
