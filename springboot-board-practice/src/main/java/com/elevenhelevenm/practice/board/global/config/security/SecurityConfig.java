package com.elevenhelevenm.practice.board.global.config.security;

import com.elevenhelevenm.practice.board.domain.user.model.Role;
import com.elevenhelevenm.practice.board.global.config.security.auth.ip.CustomAuthenticationFilter;
import com.elevenhelevenm.practice.board.global.config.security.auth.ip.CustomAuthenticationProvider;
import com.elevenhelevenm.practice.board.global.config.security.auth.usernamepassword.PrincipalDetailsService;
import com.elevenhelevenm.practice.board.global.config.security.auth.ip.PrincipalSuperAdminDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@RequiredArgsConstructor
@EnableWebSecurity
@Configuration
public class SecurityConfig {

    private final PrincipalDetailsService principalDetailsService;

    private final PrincipalSuperAdminDetailsService principalSuperAdminDetailsService;

    private final AuthenticationSuccessHandler customAuthSuccessHandler;

    private final AuthenticationFailureHandler customAuthFailureHandler;

    @Bean
    public BCryptPasswordEncoder encodePassword() {
        return new BCryptPasswordEncoder();
    }

    //API
    @Order(0)
    @Configuration
    class ApiConfig extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/api/**").authorizeRequests()
                        .antMatchers("/api/v1/joinProc/**", "/api/v2/joinProc/**", "/api/v1/login").permitAll()
                        .antMatchers("/api/v1/user/**").hasAnyRole(Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                        .anyRequest().authenticated();
        }
    }

    //사용자
    @Order(1)
    @Configuration
    class UserConfig extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .addFilterAfter(customAuthenticationFilter(), CsrfFilter.class)
                    .authorizeRequests()
                        .antMatchers("/login/**", "/join/**").permitAll()
                        .antMatchers("/member/**").hasAnyRole(Role.MEMBER.name(), Role.SUPER_ADMIN.name())
                        .antMatchers("/manager/**").hasAnyRole(Role.MANAGER.name(), Role.SUPER_ADMIN.name())
                        .antMatchers("/admin/**").hasAnyRole(Role.ADMIN.name(), Role.SUPER_ADMIN.name())
                .and()
                    .csrf()
                        .ignoringAntMatchers("/admin/h2-console/**")
                .and()
                    .headers().frameOptions().sameOrigin()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .successHandler(customAuthSuccessHandler)
                        .failureHandler(customAuthFailureHandler)
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                .and()
                    .exceptionHandling()
                        .accessDeniedPage("/errors/denied");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            auth
                    .authenticationProvider(authIpProvider())
                    .authenticationProvider(authUsernamePasswordProvider());
        }

        @Bean
        public DaoAuthenticationProvider authUsernamePasswordProvider() {
            DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
            authenticationProvider.setPasswordEncoder(encodePassword());
            authenticationProvider.setUserDetailsService(principalDetailsService);
            return authenticationProvider;
        }

        @Bean
        public CustomAuthenticationProvider authIpProvider() {
            CustomAuthenticationProvider customAuthenticationProvider = new CustomAuthenticationProvider(principalSuperAdminDetailsService);
            return customAuthenticationProvider;
        }

        @Bean
        public CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
            CustomAuthenticationFilter filter = new CustomAuthenticationFilter(
                    new AntPathRequestMatcher("/api/v1/login", HttpMethod.POST.name())
            );

            filter.setAuthenticationSuccessHandler(new SimpleUrlAuthenticationSuccessHandler("/"));
            filter.setAuthenticationFailureHandler(new SimpleUrlAuthenticationFailureHandler("/super-admin-login?error"));
            filter.setAuthenticationManager(authenticationManagerBean());

            return filter;
        }

        @Bean
        @Override
        public AuthenticationManager authenticationManagerBean() throws Exception {
            return super.authenticationManagerBean();
        }
    }

/*
    //다중 로그인 시 설정 방법
    
    //관리자
    @Order(1)
    @Configuration
    class AdminConfig extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); //SpringBoot에서 제공하는 설정 사용
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/admin/**").authorizeRequests()
                        .antMatchers("/admin/login/**", "/admin/join/**").permitAll()
                        .anyRequest().hasRole(Role.ADMIN.name())
                .and()
                    .csrf()
                       .ignoringAntMatchers("/admin/h2-console/**") //해당 URL에 대해 CSRF 필터 적용 무시
                .and()
                    .headers().frameOptions().sameOrigin() //해당 페이지와 동일한 origin을 갖는 경우 frame 표시 가능
                .and()
                    .formLogin()
                        .loginPage("/admin/login")
                        .loginProcessingUrl("/admin/loginProc")
                        .successHandler(customAuthSuccessHandler)
                        .failureHandler(customAuthFailureHandler)
                .and()
                    .logout()
                        .logoutSuccessUrl("/admin/login")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                .and()
                    .exceptionHandling()
                       .accessDeniedPage("/errors/denied"); //로그인 한 경우만 적용 (인증된 사용자가 아니라면 로그인 페이지로 이동)
        }
    }

    //일반 유저
    @Order(2)
    @Configuration
    class UserConfig extends WebSecurityConfigurerAdapter {
        @Override
        public void configure(WebSecurity web) throws Exception {
            web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations()); //SpringBoot에서 제공하는 설정 사용
        }

        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                    .antMatcher("/**").authorizeRequests()
                        .antMatchers("/", "/join", "/login", "/error/**").permitAll()
                        .anyRequest().authenticated()
                .and()
                    .formLogin()
                        .loginPage("/login")
                        .loginProcessingUrl("/loginProc")
                        .successHandler(customAuthSuccessHandler)
                        .failureHandler(customAuthFailureHandler)
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                        .deleteCookies("JSESSIONID")
                        .invalidateHttpSession(true)
                .and()
                    .exceptionHandling()
                        .accessDeniedPage("/errors/denied"); //로그인 한 경우만 적용 (인증된 사용자가 아니라면 로그인 페이지로 이동)
        }
    }
*/
}
