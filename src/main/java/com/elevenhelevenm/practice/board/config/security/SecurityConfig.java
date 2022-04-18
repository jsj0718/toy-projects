package com.elevenhelevenm.practice.board.config.security;

import com.elevenhelevenm.practice.board.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
                .cors().disable() //교차 출저 리소스 공유
                .csrf().disable() //사이트 간 요청 위조 (쿠키가 CSRF의 매개체)
                .headers().frameOptions().disable() //X-Frame-Options header 추가 부분 disable (iframe 사용을 위함)
            .and()
                .authorizeRequests()
                    .antMatchers("/css/**", "/js/**", "/images/**", "/h2-console/**", "/", "/login/**", "/join/**", "/api/v1/joinProc/**").permitAll()
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
