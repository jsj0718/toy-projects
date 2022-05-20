package com.elevenhelevenm.practice.board.domain.membership.service;

import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipAddResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipDetailResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.model.Membership;
import com.elevenhelevenm.practice.board.domain.membership.model.MembershipType;
import com.elevenhelevenm.practice.board.domain.membership.repository.MembershipRepository;
import com.elevenhelevenm.practice.board.global.errors.code.MembershipErrorCode;
import com.elevenhelevenm.practice.board.global.errors.exception.MembershipException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MembershipServiceTest {

    @InjectMocks
    MembershipService membershipService;

    @Mock
    MembershipRepository membershipRepository;

    @Mock
    PointService ratePointService;

    @Test
    void 이미_멤버십이_존재한다면_멤버십등록은_실패한다() {
        //given
        String userId = "userId";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 10000;

        Membership membership = Membership.builder()
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        given(membershipRepository.findByUserIdAndMembershipType(userId, membershipType))
                .willReturn(Optional.of(membership));

        //when
        MembershipException result = assertThrows(MembershipException.class,
                () -> membershipService.addMembership(userId, membershipType, point));

        //then
        assertThat(result.getErrorCode()).isEqualTo(MembershipErrorCode.DUPLICATED_MEMBERSHIP_REGISTER);
    }

    @Test
    void 멤버십_등록에_성공한다() {
        //given
        String userId = "userId";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 10000;

        Membership membership = Membership.builder()
                .id(1L)
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        given(membershipRepository.findByUserIdAndMembershipType(userId, membershipType))
                .willReturn(Optional.empty());

        given(membershipRepository.save(any(Membership.class)))
                .willReturn(membership);

        //when
        MembershipAddResponseDto membershipResponseDto = membershipService.addMembership(userId, membershipType, point);

        //then
        assertThat(membershipResponseDto.getId()).isNotNull();
        assertThat(membershipResponseDto.getUserId()).isEqualTo(userId);
        assertThat(membershipResponseDto.getMembershipType()).isEqualTo(membershipType);
        assertThat(membershipResponseDto.getPoint()).isEqualTo(point);

        //verify
        verify(membershipRepository, times(1)).findByUserIdAndMembershipType(userId, membershipType);
        verify(membershipRepository, times(1)).save(any(Membership.class));
    }

    @Test
    void 멤버십목록조회() {
        //given
        String userId = "test";

        given(membershipRepository.findAllByUserId(userId))
                .willReturn(Arrays.asList(
                        Membership.builder().build(),
                        Membership.builder().build(),
                        Membership.builder().build()));

        //when
        List<MembershipDetailResponseDto> memberships = membershipService.getMemberships(userId);

        //then
        assertThat(memberships).hasSize(3);
    }

    @Test
    void 멤버십상세조회실패_멤버십존재하지않음() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipRepository.findById(membershipId)).willReturn(Optional.empty());

        //when
        MembershipException result = Assertions.assertThrows(MembershipException.class,
                () -> membershipService.getMembership(membershipId, userId));

        //then
        assertThat(result.getErrorCode()).isEqualTo(MembershipErrorCode.MEMBERSHIP_NOT_FOUND);
    }

    @Test
    void 멤버십상세조회실패_본인이아님() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipRepository.findById(membershipId)).willReturn(Optional.of(Membership.builder()
                .id(membershipId)
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build()));

        //when
        MembershipException result = Assertions.assertThrows(MembershipException.class,
                () -> membershipService.getMembership(membershipId, "notOwner"));

        //then
        assertThat(result.getErrorCode()).isEqualTo(MembershipErrorCode.NOT_MEMBERSHIP_OWNER);
    }

    @Test
    void 멤버십상세조회성공() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipRepository.findById(membershipId)).willReturn(Optional.of(Membership.builder()
                .id(membershipId)
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build()));

        //when
        MembershipDetailResponseDto responseDto = membershipService.getMembership(membershipId, userId);

        //then
        assertThat(responseDto.getId()).isEqualTo(membershipId);
        assertThat(responseDto.getUserId()).isEqualTo(userId);
        assertThat(responseDto.getMembershipType()).isEqualTo(MembershipType.NAVER);
        assertThat(responseDto.getPoint()).isEqualTo(10000);
    }

    @Test
    void 멤버십삭제실패_존재하지않음() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipRepository.findById(membershipId))
                .willReturn(Optional.empty());

        //when
        MembershipException exception = assertThrows(MembershipException.class,
                () -> membershipService.removeMembership(membershipId, userId));

        //then
        assertThat(exception.getErrorCode()).isEqualTo(MembershipErrorCode.MEMBERSHIP_NOT_FOUND);
    }

    @Test
    void 멤버십삭제실패_본인이아님() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        Membership membership = Membership.builder()
                .id(membershipId)
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        given(membershipRepository.findById(membershipId))
                .willReturn(Optional.of(membership));

        //when
        MembershipException exception = assertThrows(MembershipException.class,
                () -> membershipService.removeMembership(membershipId, "notOwner"));

        //then
        assertThat(exception.getErrorCode()).isEqualTo(MembershipErrorCode.NOT_MEMBERSHIP_OWNER);
    }

    @Test
    void 멤버십삭제성공() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipRepository.findById(membershipId))
                .willReturn(Optional.of(Membership.builder()
                        .id(membershipId)
                        .userId(userId)
                        .build()));

        //when & then
        membershipService.removeMembership(membershipId, userId);
    }

    @Test
    void 멤버십적립실패_존재하지않음() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        given(membershipRepository.findById(membershipId))
                .willReturn(Optional.empty());

        //when
        MembershipException exception = Assertions.assertThrows(MembershipException.class,
                () -> membershipService.accumulateMembershipPoint(membershipId, userId, 10000));

        //then
        assertThat(exception.getErrorCode()).isEqualTo(MembershipErrorCode.MEMBERSHIP_NOT_FOUND);
    }

    @Test
    void 멤버십적립실패_본인이아님() {
        //given
        Long membershipId = 1L;
        String userId = "test";

        Membership membership = Membership.builder()
                .id(membershipId)
                .userId(userId)
                .build();

        given(membershipRepository.findById(membershipId))
                .willReturn(Optional.of(membership));

        //when
        MembershipException exception = Assertions.assertThrows(MembershipException.class,
                () -> membershipService.accumulateMembershipPoint(membershipId, "notOwner", 10000));

        //then
        assertThat(exception.getErrorCode()).isEqualTo(MembershipErrorCode.NOT_MEMBERSHIP_OWNER);
    }

    @Test
    void 멤버십적립성공() {
        //given
        Long membershipId = 1L;
        String userId = "test";
        int amount = 10000;
        int rate = 1;

        Membership membership = Membership.builder()
                .id(membershipId)
                .userId(userId)
                .build();

        given(membershipRepository.findById(membershipId))
                .willReturn(Optional.of(membership));

        given(ratePointService.calculateAmount(amount))
                .willReturn(amount * rate / 100);

        //when & then
        membershipService.accumulateMembershipPoint(membershipId, userId, amount);
    }

}