package com.elevenhelevenm.practice.board.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing //JPA의 Audit 기능을 사용하기 위한 Annotation (생성일자, 수정일자 등)
public class JpaConfig {
}
