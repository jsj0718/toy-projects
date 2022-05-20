package com.elevenhelevenm.practice.board.global.config.security.auth.ip;

import com.elevenhelevenm.practice.board.domain.user.model.SuperAdmin;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Data
public class PrincipalSuperAdminDetails implements UserDetails {

    private SuperAdmin superAdmin;

    public PrincipalSuperAdminDetails(SuperAdmin superAdmin) {
        this.superAdmin = superAdmin;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(() -> superAdmin.getRole().getKey());

        return collection;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return superAdmin.getIp();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
