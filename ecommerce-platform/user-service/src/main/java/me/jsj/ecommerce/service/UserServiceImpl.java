package me.jsj.ecommerce.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import me.jsj.ecommerce.domain.UserEntity;
import me.jsj.ecommerce.domain.UserRepository;
import me.jsj.ecommerce.dto.UserDto;
import me.jsj.ecommerce.vo.ResponseOrder;
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
                .orElseThrow(() -> new NotFoundException("Not found user by this userId"));

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
