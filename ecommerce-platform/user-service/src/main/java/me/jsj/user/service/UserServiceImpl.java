package me.jsj.user.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import me.jsj.user.domain.UserEntity;
import me.jsj.user.domain.UserRepository;
import me.jsj.user.dto.UserDto;
import me.jsj.user.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDto createUser(UserDto userDto) {
        ModelMapper mapper = getMapper();

        userDto.setUserId(UUID.randomUUID().toString());

        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        return mapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        ModelMapper mapper = getMapper();

        UserEntity userEntity = userRepository.findByUserId(userId)
                .orElseThrow(() -> new NotFoundException("User not found by this id: " + userId));

        UserDto userDto = mapper.map(userEntity, UserDto.class);

        ArrayList<ResponseOrder> orders = new ArrayList<>();
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public List<UserDto> getUsersByAll() {
        ModelMapper mapper = getMapper();

        return userRepository.findAll().stream().map(userEntity -> mapper.map(userEntity, UserDto.class)).toList();
    }

    private static ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
