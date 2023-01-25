package com.elevenhelevenm.practice.board.global.config.security.auth.usernamepassword;

import com.elevenhelevenm.practice.board.domain.user.model.UserV2;
import com.elevenhelevenm.practice.board.domain.user.repository.UserRepositoryV2;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepositoryV2<UserV2> userRepositoryV2;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserV2 user = userRepositoryV2.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유효하지 않은 계정입니다."));

        return new PrincipalDetailsV2(user);
    }
}
