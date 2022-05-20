package com.elevenhelevenm.practice.board.domain.membership.dto;

import com.elevenhelevenm.practice.board.domain.membership.model.Membership;
import com.elevenhelevenm.practice.board.domain.membership.model.MembershipType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class MembershipAddResponseDto implements Serializable {

    private Long id;

    private String userId;

    private MembershipType membershipType;

    private int point;

    @Builder
    public MembershipAddResponseDto(Long id, String userId, MembershipType membershipType, int point) {
        this.id = id;
        this.userId = userId;
        this.membershipType = membershipType;
        this.point = point;
    }

    public MembershipAddResponseDto(Membership membership) {
        this.id = membership.getId();
        this.userId = membership.getUserId();
        this.membershipType = membership.getMembershipType();
        this.point = membership.getPoint();
    }
}
