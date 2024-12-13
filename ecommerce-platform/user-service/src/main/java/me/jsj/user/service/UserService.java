package me.jsj.user.service;

import me.jsj.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserDto> getUsersByAll();
    UserDto getUserDetailsByEmail(String username);
}
