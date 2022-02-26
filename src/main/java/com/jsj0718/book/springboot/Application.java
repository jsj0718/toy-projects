package com.jsj0718.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 앞으로 만들 프로젝트의 메인 클래스 -> 이 클래스가 모든 프로젝트의 최상단에 위치해야 함
 * @SpringBootApplication으로 스프링 부트의 자동 설정, 스프링 빈 읽기 및 생성을 모두 자동으로 설정
 */
@EnableJpaAuditing // JPA Auditing 기능 활성화
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
