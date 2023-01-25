package com.elevenhelevenm.practice.board.domain.membership.dto;

import com.elevenhelevenm.practice.board.domain.membership.model.MembershipType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
public class MembershipRequestDto implements Serializable {

    @NotNull
    private MembershipType membershipType;

    @Min(0)
    @NotNull
    private Integer point;

    @Builder
    public MembershipRequestDto(MembershipType membershipType, Integer point) {
        this.membershipType = membershipType;
        this.point = point;
    }
}
