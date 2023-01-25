package com.elevenhelevenm.practice.board.global.config.security.auth.usernamepassword;

import com.elevenhelevenm.practice.board.domain.user.model.UserV2;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

//Authentication 객체에 저장할 수 있는 유일한 타입
@Slf4j
@Data
public class PrincipalDetailsV2 implements UserDetails {

    private UserV2 user;

    public PrincipalDetailsV2(UserV2 user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> user.getRole().getKey());

        collection.forEach(grantedAuthority -> log.info("권한 : {}", grantedAuthority.getAuthority()));

        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
