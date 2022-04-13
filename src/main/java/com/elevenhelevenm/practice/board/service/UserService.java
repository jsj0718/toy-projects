package com.elevenhelevenm.practice.board.service;

import com.elevenhelevenm.practice.board.domain.user.User;
import com.elevenhelevenm.practice.board.exception.UserException;
import com.elevenhelevenm.practice.board.exception.errorcode.UserErrorCode;
import com.elevenhelevenm.practice.board.repository.UserRepository;
import com.elevenhelevenm.practice.board.web.dto.request.user.UserSaveRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class UserService {

    private final BCryptPasswordEncoder passwordEncoder;

    private final UserRepository userRepository;

    @Transactional
    public Long join(UserSaveRequestDto requestDto) {
        requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));

        User user = requestDto.toEntity();

        if (!userRepository.findByUsername(user.getUsername()).isEmpty()) {
            throw new UserException(UserErrorCode.FoundDuplicatedId);
        }

        return userRepository.save(user).getId();
    }

}


