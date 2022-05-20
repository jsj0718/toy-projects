package com.elevenhelevenm.practice.board.domain.membership.service;

import com.elevenhelevenm.practice.board.domain.membership.repository.MembershipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class RatePointService implements PointService {

    private static final int POINT_RATE = 1;

    private final MembershipRepository membershipRepository;

    public int calculateAmount(int price) {
        return price * POINT_RATE / 100;
    }

}
