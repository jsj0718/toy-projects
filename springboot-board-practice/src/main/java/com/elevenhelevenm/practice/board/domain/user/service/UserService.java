package com.elevenhelevenm.practice.board.domain.user.service;

import com.elevenhelevenm.practice.board.domain.user.dto.request.UserSaveRequestDto;
import com.elevenhelevenm.practice.board.domain.user.dto.request.UserV2SaveRequestDto;
import com.elevenhelevenm.practice.board.domain.user.dto.response.UserResponseDto;
import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.domain.user.model.User;
import com.elevenhelevenm.practice.board.domain.user.model.UserV2;
import com.elevenhelevenm.practice.board.domain.user.repository.UserRepository;
import com.elevenhelevenm.practice.board.domain.user.repository.UserRepositoryV2;
import com.elevenhelevenm.practice.board.global.errors.code.UserErrorCode;
import com.elevenhelevenm.practice.board.global.errors.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    private final UserRepositoryV2<UserV2> userRepositoryV2;

    @Transactional
    public Long join(UserSaveRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User user = requestDto.toEntity();

        //아이디가 같은 경우
        if (!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            throw new CustomException(UserErrorCode.FoundDuplicatedId);
        }

        //이메일이 같은 경우
        if (!userRepository.findByEmail(user.getEmail()).isEmpty()) {
            throw new CustomException(UserErrorCode.FoundDuplicatedEmail);
        }

        return userRepository.save(user).getId();
    }

    @Transactional
    public Long joinV2(UserV2SaveRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        UserV2 user = null;
        Role role = requestDto.getRole();

        if (role.equals(Role.MEMBER)) user = requestDto.toMember();
        else if (role.equals(Role.MANAGER)) user = requestDto.toManager();
        else if (role.equals(Role.ADMIN)) user = requestDto.toAdmin();

        //권한이 유효하지 않은 경우
        if (user == null) throw new CustomException(UserErrorCode.InvalidRoleType);

        //아이디가 같은 경우
        if (!userRepositoryV2.findByUsername(user.getUsername()).isEmpty()) {
            throw new CustomException(UserErrorCode.FoundDuplicatedId);
        }

        //이메일이 같은 경우
        if (!userRepositoryV2.findByEmail(user.getEmail()).isEmpty()) {
            throw new CustomException(UserErrorCode.FoundDuplicatedEmail);
        }

        return userRepositoryV2.save(user).getId();
    }

    @Transactional
    public void delete(Long id) {
        UserV2 findUser = userRepositoryV2.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다."));

        if (findUser.getRole().equals(Role.ADMIN)) {
            throw new CustomException(UserErrorCode.FailToDeleteAdmin);
        }

        userRepositoryV2.delete(findUser);
    }

    public List<UserResponseDto> findAll() {
        return userRepositoryV2.findAll().stream()
                .map(UserResponseDto::new)
                .collect(Collectors.toList());
    }

    public UserResponseDto findById(Long id) {
        return new UserResponseDto(userRepositoryV2.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 사용자입니다.")));
    }

}


