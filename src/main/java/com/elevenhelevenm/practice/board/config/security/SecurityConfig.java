package com.elevenhelevenm.practice.board.config.security;

import com.elevenhelevenm.practice.board.config.security.auth.PrincipalDetails;
import com.elevenhelevenm.practice.board.config.security.auth.PrincipalDetailsService;
import com.elevenhelevenm.practice.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final AuthenticationFailureHandler customFailureHandler;

    @Bean
    public BCryptPasswordEncoder encodePwd() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .cors().disable()
                .csrf().disable()
                .headers().frameOptions().disable()
            .and()
                .authorizeRequests()
                    .antMatchers("/", "/css/**", "/js/**", "/images/**", "/h2-console/**", "/join/**", "/joinProc", "/login/**").permitAll()
                    .antMatchers("/admin/**").access(Role.ADMIN.name())
                    .anyRequest().authenticated()
            .and()
                .logout()
                    .logoutSuccessUrl("/")
            .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/loginProc")
                .defaultSuccessUrl("/")
                .failureHandler(customFailureHandler);
    }
}
