package com.elevenhelevenm.practice.board.domain.money;

public interface Expression {
    Money reduce(Bank bank, String to);

    Expression plus(Expression addend);

    Expression times(int times);
}
