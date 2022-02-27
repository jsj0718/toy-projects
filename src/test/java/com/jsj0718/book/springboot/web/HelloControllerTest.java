package com.jsj0718.book.springboot.web;

import com.jsj0718.book.springboot.config.auth.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @WebMvcTest는 @Contoller, @ControllerAdvice만을 읽는다. (@Repository, @Service, @Component X)
 * 따라서 SecurityConfig는 읽어도, 이를 생성하기 위해 필요한 CustomOAuth2UserService를 스캔하지 못한다.
 * 이를 해결하기 위해 먼저 SecurityConfig 스캔 대상에서 제외한다.
 *
 * 또한 JPA 관련 오류(@EnableJpaAuditing)가 발생하는데, 이를 사용하기 위해서는 최소한 하나의 @Entity 클래스가 필요하다.
 * -> @EnableJpaAuditing과 SpringBootApplication이 붙어 있어서 발생했기 때문에 분리하면 된다. (config 패키지에 별도로 분리하여 만든다.)
 */
@WebMvcTest(controllers = HelloController.class,
        excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = SecurityConfig.class)
})
class HelloControllerTest {

    @Autowired
    MockMvc mvc;

    @Test
    @WithMockUser(roles = "USER")
    void hello가_리턴된다() throws Exception {
        String hello = "hello";
        mvc.perform(get("/hello"))
                .andExpect(status().isOk())
                .andExpect(content().string(hello));
//        log.info("hi");
    }
    
    @Test
    @WithMockUser(roles = "USER")
    void helloDto가_리턴된다() throws Exception {
        String name = "hello";
        int amount = 1000;

        mvc.perform(get("/hello/dto")
                        .param("name", name)
                        .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(name))
                .andExpect(jsonPath("$.amount").value(amount));
    }
}