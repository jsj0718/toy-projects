package com.elevenhelevenm.practice.board.domain.membership.repository;

import com.elevenhelevenm.practice.board.domain.membership.model.Membership;
import com.elevenhelevenm.practice.board.domain.membership.model.MembershipType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MembershipRepositoryTest {

    @Autowired
    MembershipRepository membershipRepository;

    @AfterEach
    void after() {
        membershipRepository.deleteAll();
    }

    @Test
    void 멤버십을_등록한다() {
        //given
        String userId = "userId";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 1000;

        Membership membership = Membership.builder()
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        //when
        Membership savedMembership = membershipRepository.save(membership);

        //then
        assertThat(savedMembership).isNotNull();
        assertThat(savedMembership.getId()).isNotNull();
        assertThat(savedMembership.getUserId()).isEqualTo(userId);
        assertThat(savedMembership.getMembershipType()).isEqualTo(membershipType);
        assertThat(savedMembership.getPoint()).isEqualTo(point);
    }
    
    @Test
    void 멤버십이_존재하는지_확인한다() {
        //given
        String userId = "userId";
        MembershipType membershipType = MembershipType.NAVER;
        int point = 1000;

        Membership membership = Membership.builder()
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build();

        //when
        membershipRepository.save(membership);
        Membership findMembership = membershipRepository.findByUserIdAndMembershipType(userId, membershipType).get();

        //then
        assertThat(findMembership).isNotNull();
        assertThat(findMembership.getId()).isNotNull();
        assertThat(findMembership.getUserId()).isEqualTo(userId);
        assertThat(findMembership.getMembershipType()).isEqualTo(membershipType);
        assertThat(findMembership.getPoint()).isEqualTo(point);
    }

    @Test
    void 동일ID_멤버십조회_사이즈0() {
        //given
        String userId = "test";

        //when
        List<Membership> memberships = membershipRepository.findAllByUserId(userId);

        //then
        assertThat(memberships).hasSize(0);
    }

    @Test
    void 동일ID_멤버십조회_사이즈2() {
        //given
        String userId = "test";

        membershipRepository.save(Membership.builder()
                .userId(userId)
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build());

        membershipRepository.save(Membership.builder()
                .userId(userId)
                .membershipType(MembershipType.KAKAO)
                .point(10000)
                .build());

        //when
        List<Membership> memberships = membershipRepository.findAllByUserId(userId);

        //then
        assertThat(memberships).hasSize(2);
    }
    
    @Test
    void 멤버십삭제() {
        //given
        Membership membership = Membership.builder()
                .userId("test")
                .membershipType(MembershipType.NAVER)
                .point(10000)
                .build();

        Membership savedMembership = membershipRepository.save(membership);

        //when
        membershipRepository.deleteById(membership.getId());

        //then
        Optional<Membership> findMembership = membershipRepository.findById(savedMembership.getId());
        Assertions.assertThat(findMembership).isEmpty();
    }
}