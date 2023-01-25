package com.elevenhelevenm.practice.board.global.config.convert;

import com.elevenhelevenm.practice.board.domain.user.model.Role;
import org.springframework.core.convert.converter.Converter;


public class StringToRoleConverter implements Converter<String, Role> {


    @Override
    public Role convert(String source) {
        return Role.valueOf(source.toUpperCase());
    }
}
