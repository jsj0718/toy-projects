package me.jsj.user.service;

import jakarta.ws.rs.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.jsj.user.client.OrderServiceClient;
import me.jsj.user.domain.UserEntity;
import me.jsj.user.domain.UserRepository;
import me.jsj.user.dto.UserDto;
import me.jsj.user.vo.ResponseOrder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    private final Environment env;
    private final BCryptPasswordEncoder passwordEncoder;

//    private final RestTemplate restTemplate;
    private final OrderServiceClient orderServiceClient;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found by this email: " + username));

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true, true, true,
                new ArrayList<>());
    }

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

        /* Use RestTemplate */
//        String orderUrl = String.format(Objects.requireNonNull(env.getProperty("order-service.url")), userId);
//        ResponseEntity<List<ResponseOrder>> responseOrdersResponse = restTemplate.exchange(
//                orderUrl, HttpMethod.GET,
//                null, new ParameterizedTypeReference<>() {});
//        List<ResponseOrder> orders = responseOrdersResponse.getBody();

        /* Use Feign Client */
        /* Feign Client Exception handling (try-catch) */
//        List<ResponseOrder> orders = null;
//        try {
//            orders = orderServiceClient.getOrders(userId);
//        } catch (FeignException e) {
//            log.error(e.getMessage());
//        }

        List<ResponseOrder> orders = orderServiceClient.getOrders(userId);
        userDto.setOrders(orders);

        return userDto;
    }

    @Override
    public List<UserDto> getUsersByAll() {
        ModelMapper mapper = getMapper();

        return userRepository.findAll().stream().map(userEntity -> mapper.map(userEntity, UserDto.class)).toList();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        ModelMapper mapper = getMapper();

        UserEntity userEntity = userRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundException("User not found by this email: " + email));

        return mapper.map(userEntity, UserDto.class);
    }

    private static ModelMapper getMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }
}
