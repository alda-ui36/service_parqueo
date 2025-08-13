package com.shujaa.parking_system.application.dtos.response;

import lombok.*;
import java.time.Instant;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {
    private String accessToken;
    private String refreshToken;
    private Integer userId;
    private String username;
    private String email;
    private Set<String> roles;
} 