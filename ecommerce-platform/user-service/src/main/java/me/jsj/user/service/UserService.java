package me.jsj.user.service;

import me.jsj.user.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    List<UserDto> getUsersByAll();
}
