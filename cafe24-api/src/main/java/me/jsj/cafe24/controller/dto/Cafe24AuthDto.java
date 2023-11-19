package me.jsj.cafe24.controller.dto;

import lombok.Data;
import org.apache.commons.codec.binary.Base64;

import java.time.LocalDateTime;
import java.util.List;

public class Cafe24AuthDto {
    @Data
    public static class Home {
        private String mallId;
        private String clientId;
        private String clientSecret;

        public String encode() {
            return new String(Base64.encodeBase64((clientId + ":" + clientSecret).getBytes()));
        }
    }

    @Data
    public static class Auth {
        private String code;
        private String state;
        private String error;
        private String trace_id;
    }

    @Data
    public static class AccessToken {
        private String accessToken;
        private LocalDateTime expiresAt;
        private String refreshToken;
        private LocalDateTime refreshTokenExpiresAt;
        private String clientId;
        private String mallId;
        private String userId;
        private List<String> scopes;
        private LocalDateTime issuedDate;
    }
}
