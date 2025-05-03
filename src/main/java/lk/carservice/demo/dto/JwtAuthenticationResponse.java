package lk.carservice.demo.dto;

import lombok.Data;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private Integer userId;
    private String email;
    private String role;

    public JwtAuthenticationResponse(String accessToken, Integer userId, String email, String role) {
        this.accessToken = accessToken;
        this.userId = userId;
        this.email = email;
        this.role = role;
    }
}
