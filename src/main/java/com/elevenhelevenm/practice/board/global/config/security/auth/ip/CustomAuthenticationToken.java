package com.elevenhelevenm.practice.board.global.config.security.auth.ip;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.Collections;

public class CustomAuthenticationToken extends AbstractAuthenticationToken {

    private String ip;

    public CustomAuthenticationToken(String ip) {
        super(Collections.emptyList());
        this.ip = ip;
    }

    public CustomAuthenticationToken(String ip, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.ip = ip;
    }

    public CustomAuthenticationToken(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }


    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return ip;
    }
}
