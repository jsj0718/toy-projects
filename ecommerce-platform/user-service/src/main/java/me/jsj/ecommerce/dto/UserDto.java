package me.jsj.ecommerce.dto;

import lombok.Data;
import me.jsj.ecommerce.vo.ResponseOrder;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class UserDto {
    private String email;
    private String name;
    private String pwd;
    private String userId;
    private LocalDateTime createdAt;
    private String encryptedPwd;

    private List<ResponseOrder> orders;
}
