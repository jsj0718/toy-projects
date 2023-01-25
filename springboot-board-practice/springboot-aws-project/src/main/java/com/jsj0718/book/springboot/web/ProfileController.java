package com.jsj0718.book.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProfileController {

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        //env.getActiveProfiles() : 현재 실행 중인 ActiveProfile을 모두 가져온다. (real, oauth, real-db 등)
        List<String> profiles = Arrays.asList(env.getActiveProfiles());

        //real, real1, real2는 모두 배포에 사용될 profile이라 이 중 하나만 있어도 그 값을 반환 (무중단 배포에선 real1, real2만 사용)
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");

        String defaultProfile = profiles.isEmpty() ? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles::contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
