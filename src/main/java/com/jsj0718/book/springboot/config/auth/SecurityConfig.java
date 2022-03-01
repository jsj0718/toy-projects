package com.jsj0718.book.springboot.config.auth;

import com.jsj0718.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.stereotype.Repository;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/css/**", "/js/**", "/images/**", "/h2-console/**", "/login").permitAll()
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name()) //USER만 허용
                    .anyRequest().authenticated() //GUEST, USER 허용
                .and()
                    .oauth2Login().loginPage("/")
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login() //OAuth2 로그인 기능 설정 진입점
                        .userInfoEndpoint() //OAuth2 로그인 성공 이후 사용자 정보를 가져올 때의 설정 담당
                            .userService(customOAuth2UserService); //소셜 로그인 성공 이후 후속 조치를 진행할 UserService 인터페이스 구현체 등록
    }
}
