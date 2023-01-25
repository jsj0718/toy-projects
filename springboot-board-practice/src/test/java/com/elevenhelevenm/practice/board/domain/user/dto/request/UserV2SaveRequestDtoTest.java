package com.elevenhelevenm.practice.board.domain.user.dto.request;

import com.elevenhelevenm.practice.board.domain.user.model.Role;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class UserV2SaveRequestDtoTest {

    ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
    Validator validator = factory.getValidator();

    @Test
    @DisplayName("회원가입 시 각 필드에 대해 검증을 한다.")
    void userDtoValidation() {
        //필드 선언
        String blankUsername = "";
        String blankPassword = "";
        String blankEmail = "";

        String username = "username";
        String password = "password";
        String email = "email";

        Role role = Role.MEMBER;

        //공백 검증
        UserV2SaveRequestDto blankRequestDto = UserV2SaveRequestDto.builder()
                .username(blankUsername)
                .password(blankPassword)
                .email(blankEmail)
                .role(role)
                .build();

        Set<ConstraintViolation<UserV2SaveRequestDto>> blankViolations = validator.validate(blankRequestDto);
        assertThat(blankViolations.size()).isEqualTo(3);
        for (ConstraintViolation<UserV2SaveRequestDto> blankViolation : blankViolations) {
            assertThat(blankViolation.getMessage()).contains("공백은 포함될 수 없습니다.");
        }

        //Null 검증
        UserV2SaveRequestDto nullRequestDto = UserV2SaveRequestDto.builder().build();

        Set<ConstraintViolation<UserV2SaveRequestDto>> nullViolations = validator.validate(nullRequestDto);
        assertThat(nullViolations.size()).isEqualTo(4);
        for (ConstraintViolation<UserV2SaveRequestDto> nullViolation : nullViolations) {
            assertThat(nullViolation.getMessage()).contains("널은 허용되지 않습니다.");
        }

        //이메일 검증
        UserV2SaveRequestDto emailFormatRequestDto = UserV2SaveRequestDto.builder()
                .username(username)
                .password(password)
                .email(email)
                .role(role)
                .build();

        Set<ConstraintViolation<UserV2SaveRequestDto>> emailFormatViolations = validator.validate(emailFormatRequestDto);
        assertThat(emailFormatViolations.size()).isEqualTo(1);
        for (ConstraintViolation<UserV2SaveRequestDto> emailFormatViolation : emailFormatViolations) {
            assertThat(emailFormatViolation.getMessage()).contains("이메일 주소가 유효하지 않습니다.");
        }
    }

}