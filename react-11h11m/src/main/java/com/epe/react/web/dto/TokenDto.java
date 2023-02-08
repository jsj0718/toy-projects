package com.epe.react.web.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class TokenDto {
    private String grantType;
    private String accessToken;
    private Long tokenExpiresIn;
}
