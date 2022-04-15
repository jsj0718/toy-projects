package com.elevenhelevenm.practice.board.config.security.auth;

import com.elevenhelevenm.practice.board.config.security.dto.SessionUser;
import com.elevenhelevenm.practice.board.domain.user.User;
import com.elevenhelevenm.practice.board.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Service
public class PrincipalDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    private final HttpSession httpSession;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("유효하지 않은 Username 입니다."));

        if (user == null) {
            return null;
        }

        httpSession.setAttribute("user", new SessionUser(user));

        return new PrincipalDetails(user);
    }
}
