package com.epe.react.web.dto;

import com.epe.react.domain.Member;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class MemberResponseDto {
    private String email;
    private String nickname;

    public MemberResponseDto(Member member) {
        this.email = member.getEmail();
        this.nickname = member.getNickname();
    }
}
