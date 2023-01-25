package com.elevenhelevenm.practice.board.domain.membership.controller;

import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipAddResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipDetailResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipRequestDto;
import com.elevenhelevenm.practice.board.domain.membership.model.MembershipType;
import com.elevenhelevenm.practice.board.domain.membership.service.MembershipService;
import com.elevenhelevenm.practice.board.global.errors.code.MembershipErrorCode;
import com.elevenhelevenm.practice.board.global.errors.exception.MembershipException;
import com.elevenhelevenm.practice.board.global.errors.handler.ExceptionControllerAdvice;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@ExtendWith(MockitoExtension.class)
class MembershipControllerTest {

    @InjectMocks
    MembershipController membershipController;

    @Mock
    MembershipService membershipService;

    MockMvc mvc;

    static final String USER_ID_HEADER = "X-USER-ID";

    @BeforeEach
    void init() {
        mvc = MockMvcBuilders
                .standaloneSetup(membershipController)
                .setControllerAdvice(new ExceptionControllerAdvice())
                .build();
    }

    @Test
    void 멤버십등록실패_사용자식별값이_헤더에_없다() throws Exception {
        //given
        String userId = "test";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 10000;

        MembershipRequestDto requestDto = MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();

        String url = "/api/v1/memberships";

        //when & then
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void 멤버십등록실패_MemberService에서_에러발생() throws Exception {
        //given
        String userId = "test";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 10000;

        MembershipRequestDto requestDto = MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();

        given(membershipService.addMembership(userId, requestDto.getMembershipType(), requestDto.getPoint()))
                .willThrow(new MembershipException(MembershipErrorCode.DUPLICATED_MEMBERSHIP_REGISTER));

        String url = "/api/v1/memberships";

        //when & then
        mvc.perform(post(url)
                        .header(USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @ParameterizedTest
    @MethodSource("invalidMembershipAddParameter")
    void 멤버십등록실패_잘못된파라미터(MembershipType membershipType, Integer point) throws Exception {
        //given
        String userId = "test";

        MembershipRequestDto requestDto = MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();

        String url = "/api/v1/memberships";

        //when & then
        mvc.perform(post(url)
                        .header(USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    static Stream<Arguments> invalidMembershipAddParameter() {
        return Stream.of(
                Arguments.of(MembershipType.NAVER, null),
                Arguments.of(MembershipType.NAVER, -1),
                Arguments.of(null, 10000)
        );
    }

    @Test
    void 멤버십등록성공() throws Exception {
        //given
        String userId = "test";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 10000;

        MembershipRequestDto requestDto = MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();

        MembershipAddResponseDto responseDto = MembershipAddResponseDto.builder()
                .id(1L)
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        given(membershipService.addMembership(userId, requestDto.getMembershipType(), requestDto.getPoint()))
                .willReturn(responseDto);

        String url = "/api/v1/memberships";

        //when & then
        MvcResult result = mvc.perform(post(url)
                        .header(USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isCreated())
                .andReturn();

        String json = result.getResponse().getContentAsString();
        log.info("json : {}", json);

        ObjectMapper objectMapper = new ObjectMapper();
        MembershipAddResponseDto resultDto = objectMapper.readValue(json, MembershipAddResponseDto.class);

        assertThat(resultDto.getId()).isNotNull();
        assertThat(resultDto.getUserId()).isEqualTo(userId);
        assertThat(resultDto.getMembershipType()).isEqualTo(membershipType);
        assertThat(resultDto.getPoint()).isEqualTo(point);
    }

    @Test
    void 멤버십조회실패_사용자식별헤더값_존재X() throws Exception {
        //given
        String url = "/api/v1/memberships";

        //when & then
        mvc.perform(get(url))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 멤버십조회성공() throws Exception {
        //given
        String userId = "test";

        given(membershipService.getMemberships(userId))
                .willReturn(Arrays.asList(
                        MembershipDetailResponseDto.builder().build(),
                        MembershipDetailResponseDto.builder().build(),
                        MembershipDetailResponseDto.builder().build()
                ));

        String url = "/api/v1/memberships";

        //when & then
        mvc.perform(get(url)
                        .header(USER_ID_HEADER, userId))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void 멤버십상세조회실패_멤버십_존재X() throws Exception {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipService.getMembership(membershipId, userId))
                .willThrow(new MembershipException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

        String url = "/api/v1/memberships/" + membershipId;

        //when & then
        mvc.perform(get(url)
                        .header(USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("membershipId", membershipId.toString()))
                .andExpect(status().isNotFound());
    }
    
    @Test
    void 멤버십상세조회성공() throws Exception {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipService.getMembership(membershipId, userId))
                .willReturn(MembershipDetailResponseDto.builder().build());

        String url = "/api/v1/memberships/" + membershipId;

        //when & then
        mvc.perform(get(url)
                        .header(USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("membershipId", membershipId.toString()))
                .andExpect(status().isOk());
    }

    @Test
    void 멤버십삭제실패_사용자식별값이헤더에없음() throws Exception {
        //given
        Long membershipId = 1L;

        String url = "/api/v1/memberships/" + membershipId;

        //when & then
        mvc.perform(delete(url)
                        .param("membershipId", membershipId.toString()))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 멤버십삭제성공() throws Exception {
        //given
        Long membershipId = 1L;
        String userId = "test";

        String url = "/api/v1/memberships/" + membershipId;

        //when & then
        mvc.perform(delete(url)
                        .header(USER_ID_HEADER, userId)
                        .param("membershipId", membershipId.toString()))
                .andExpect(status().isNoContent());
    }

    @Test
    void 멤버십적립실패_사용자식별값_헤더에없음() throws Exception {
        //given
        Long membershipId = 1L;
        MembershipType membershipType = MembershipType.NAVER;
        int point = 10000;

        MembershipRequestDto requestDto = MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();

        String url = "/api/v1/memberships/accumulate/" + membershipId;

        //when & then
        mvc.perform(post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }
    
    @Test
    void 멤버십적립실패_포인트가음수() throws Exception {
        //given
        Long membershipId = 1L;
        String userId = "test";
        MembershipType membershipType = MembershipType.NAVER;
        int point = -1;

        MembershipRequestDto requestDto = MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();

        String url = "/api/v1/memberships/accumulate/" + membershipId;

        //when & then
        mvc.perform(post(url)
                        .header(USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void 멤버십적립성공() throws Exception {
        //given
        Long membershipId = 1L;
        String userId = "test";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 10000;

        MembershipRequestDto requestDto = MembershipRequestDto.builder()
                .membershipType(membershipType)
                .point(point)
                .build();

        String url = "/api/v1/memberships/accumulate/" + membershipId;

        //when & then
        mvc.perform(post(url)
                        .header(USER_ID_HEADER, userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(requestDto)))
                .andExpect(status().isOk());
    }
}