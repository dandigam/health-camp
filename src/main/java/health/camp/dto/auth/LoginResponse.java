package health.camp.dto.auth;

import health.camp.dto.user.UserResponse;

import java.time.Instant;

public class LoginResponse {

    private String token;
    private Instant expiresAt;
    private UserResponse user;

    public LoginResponse() {
    }

    public LoginResponse(String token, Instant expiresAt, UserResponse user) {
        this.token = token;
        this.expiresAt = expiresAt;
        this.user = user;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }

    public UserResponse getUser() {
        return user;
    }

    public void setUser(UserResponse user) {
        this.user = user;
    }
}
