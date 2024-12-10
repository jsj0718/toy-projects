package me.jsj.ecommerce.service;

import me.jsj.ecommerce.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserDto> getUsersByAll();
}
