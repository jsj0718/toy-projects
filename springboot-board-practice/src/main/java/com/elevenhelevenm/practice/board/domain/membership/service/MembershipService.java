package com.elevenhelevenm.practice.board.domain.membership.service;

import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipAddResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.dto.MembershipDetailResponseDto;
import com.elevenhelevenm.practice.board.domain.membership.model.Membership;
import com.elevenhelevenm.practice.board.domain.membership.model.MembershipType;
import com.elevenhelevenm.practice.board.domain.membership.repository.MembershipRepository;
import com.elevenhelevenm.practice.board.global.errors.code.MembershipErrorCode;
import com.elevenhelevenm.practice.board.global.errors.exception.MembershipException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;

    private final PointService ratePointService;

    @Transactional
    public MembershipAddResponseDto addMembership(String userId, MembershipType membershipType, int point) {
        Optional<Membership> findMembership = membershipRepository.findByUserIdAndMembershipType(userId, membershipType);

        if (!findMembership.isEmpty()) throw new MembershipException(MembershipErrorCode.DUPLICATED_MEMBERSHIP_REGISTER);

        Membership savedMembership = membershipRepository.save(Membership.builder()
                .userId(userId)
                .membershipType(membershipType)
                .point(point)
                .build());

        return new MembershipAddResponseDto(savedMembership);
    }

    public List<MembershipDetailResponseDto> getMemberships(String userId) {
        return membershipRepository.findAllByUserId(userId).stream().map(membership -> MembershipDetailResponseDto.builder()
                .id(membership.getId())
                .userId(membership.getUserId())
                .membershipType(membership.getMembershipType())
                .point(membership.getPoint())
                .build()).collect(Collectors.toList());
    }


    public MembershipDetailResponseDto getMembership(Long membershipId, String userId) {

        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new MembershipException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorCode.NOT_MEMBERSHIP_OWNER);

        return MembershipDetailResponseDto.builder()
                .id(membership.getId())
                .userId(membership.getUserId())
                .membershipType(membership.getMembershipType())
                .point(membership.getPoint())
                .build();
    }

    @Transactional
    public Long removeMembership(Long membershipId, String userId) {

        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new MembershipException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorCode.NOT_MEMBERSHIP_OWNER);

        return membershipId;
    }

    @Transactional
    public void accumulateMembershipPoint(Long membershipId, String userId, int amount) {
        Membership membership = membershipRepository.findById(membershipId)
                .orElseThrow(() -> new MembershipException(MembershipErrorCode.MEMBERSHIP_NOT_FOUND));

        if (!membership.getUserId().equals(userId))
            throw new MembershipException(MembershipErrorCode.NOT_MEMBERSHIP_OWNER);

        int additionalAmount = ratePointService.calculateAmount(amount);

        membership.setPoint(membership.getPoint() + additionalAmount);
    }

}
