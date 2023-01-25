package com.elevenhelevenm.practice.board.domain.money;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MoneyTest {

    @Test
    @DisplayName("어떤 금액을 어떤 수에 곱한 금액의 결과로 얻는다.")
    void moneyMultiplicationTest() {
        //given
        Money five = Money.dollar(5);

        //when & then
        assertThat(Money.dollar(10)).isEqualTo(five.times(2));
        assertThat(Money.dollar(15)).isEqualTo(five.times(3));
    }

    @Test
    @DisplayName("Money 동등성 테스트")
    void equalsTest() {
        assertThat(Money.dollar(5)).isEqualTo(Money.dollar(5));
        assertThat(Money.dollar(5)).isNotEqualTo(Money.dollar(6));
        assertThat(Money.won(5)).isNotEqualTo(Money.won(6));
        assertThat(Money.won(5)).isNotEqualTo(Money.won(6));

        assertThat(Money.dollar(5)).isNotEqualTo(Money.won(5));
    }

    @Test
    @DisplayName("통화가 다른 두 금액을 더하여 주어진 환율에 맞게 변한 금액을 결과로 얻는다.")
    void anotherMoneySumTest() {
        //given
        Expression fiveDollar = Money.dollar(5);
        Expression thousandWon = Money.won(1000);

        Bank bank = new Bank();
        bank.addRate("USD", "WON", 0.001);

        //when
        Money result = bank.reduce(fiveDollar.plus(thousandWon), "WON");

        //then
        assertThat(result.getAmount()).isEqualTo(6000);
    }

    @Test
    @DisplayName("같은 유형의 돈을 더한다.")
    void reduceSumTest() {
        //given
        Expression sum = new Sum(Money.dollar(3), Money.dollar(4));

        //when
        Bank bank = new Bank();
        Money result = bank.reduce(sum, "USD");

        //then
        assertThat(result).isEqualTo(Money.dollar(7));
    }

    @Test
    @DisplayName("같은 돈 유형으로 환율 변경 시 동일한 값이 나온다.")
    void reduceMoneyTest() {
        //given
        Bank bank = new Bank();

        //when
        Money result = bank.reduce(Money.dollar(1), "USD");

        //then
        assertThat(result).isEqualTo(Money.dollar(1));
    }

    @Test
    @DisplayName("1000원은 1달러로 변환 가능하다.")
    void reduceMoney() {
        //given
        Bank bank = new Bank();

        //when
        bank.addRate("WON", "USD", 1000);
        Money result = bank.reduce(Money.won(1000), "USD");

        //then
        assertThat(result).isEqualTo(Money.dollar(1));
    }

    @Test
    @DisplayName("배열 값 비교 테스트")
    void arrayEqualsTest() {
        assertThat(new Object[]{"abc"}).isEqualTo(new Object[]{"abc"});
    }

    @Test
    @DisplayName("같은 환율 시 rate는 1이다.")
    void identityRateTest() {
        assertThat(new Bank().getRate("USD", "USD")).isEqualTo(1);
    }

    @Test
    @DisplayName("Sum 클래스를 통해 서로 다른 세 통화를 더한다.")
    void sumPlusMoneyTest() {
        //given
        Expression fiveDollar = Money.dollar(5);
        Expression fiveThousandWon = Money.won(5000);

        Bank bank = new Bank();
        bank.addRate("WON", "USD", 1000);

        //when
        Expression sum = new Sum(fiveDollar, fiveThousandWon).plus(fiveDollar);
        Money result = bank.reduce(sum, "USD");

        //then
        assertThat(result).isEqualTo(Money.dollar(15));
    }

    @Test
    @DisplayName("Expression 인터페이스에 times를 추가 후 배수 테스트를 진행한다.")
    void sumTimesTest() {
        //given
        Expression fiveDollar = Money.dollar(5);
        Expression thousandWon = Money.won(1000);

        Bank bank = new Bank();
        bank.addRate("WON", "USD", 1000);

        //when
        Expression sum = new Sum(fiveDollar, thousandWon).times(2);
        Money result = bank.reduce(sum, "USD");

        //then
        assertThat(result).isEqualTo(Money.dollar(12));
    }

}