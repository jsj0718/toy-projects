package com.elevenhelevenm.practice.board.domain.membership.service;

import com.elevenhelevenm.practice.board.domain.membership.repository.MembershipRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class RatePointServiceTest {

    @InjectMocks
    RatePointService ratePointService;
    
    @Mock
    MembershipRepository membershipRepository;

    @Test
    void 적립테스트() {
        //given
        int price1 = 10000;
        int price2 = 20000;

        //when
        int result1 = ratePointService.calculateAmount(price1);
        int result2 = ratePointService.calculateAmount(price2);

        //then
        assertThat(result1).isEqualTo(100);
        assertThat(result2).isEqualTo(200);
    }
    
}