package com.elevenhelevenm.practice.board.domain.money;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Objects;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Money implements Expression {
    //Field
    @Id @GeneratedValue
    @Column(name = "MONEY_ID")
    private Long id;

    private int amount;

    private String currency;

    //Constructor
    public Money(int amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    //Factory Method
    public static Money dollar(int amount) {
        return new Money(amount, "USD");
    }

    public static Money won(int amount) {
        return new Money(amount, "WON");
    }

    //Domain Method
    @Override
    public Expression times(int times) {
        return new Money(amount * times, currency);
    }

    @Override
    public Expression plus(Expression addend) {
        return new Sum(this, addend);
    }

    @Override
    public Money reduce(Bank bank, String to) {
        double rate = bank.getRate(currency, to);
        return new Money((int)(amount / rate), to);
    }

    //Hashcode and Equals
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Money money = (Money) o;
        return amount == money.amount && currency.equals(money.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount, currency);
    }
}
