package com.elevenhelevenm.practice.board.domain.membership.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MembershipType {

    NAVER("네이버"), KAKAO("카카오"), GOOGLE("구글");

    private final String companyName;
}
