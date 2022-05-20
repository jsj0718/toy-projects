package com.elevenhelevenm.practice.board.global.config.security.auth.ip;

import com.elevenhelevenm.practice.board.domain.user.model.SuperAdmin;
import com.elevenhelevenm.practice.board.domain.user.repository.SuperAdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PrincipalSuperAdminDetailsService implements UserDetailsService {

    private final SuperAdminRepository superAdminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SuperAdmin superAdmin = superAdminRepository.findByIp(username)
                .orElseThrow(() -> new UsernameNotFoundException("등록되지 않은 IP 입니다."));

        return new PrincipalSuperAdminDetails(superAdmin);
    }
}
