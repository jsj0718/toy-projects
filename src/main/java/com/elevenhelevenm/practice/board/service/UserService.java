package com.elevenhelevenm.practice.board.service;

import com.elevenhelevenm.practice.board.domain.user.User;
import com.elevenhelevenm.practice.board.exception.CustomException;
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

}


