package com.elevenhelevenm.practice.board.domain.money;

import java.util.Hashtable;

public class Bank {

    private Hashtable<Pair, Double> rates = new Hashtable<>();

    public Money reduce(Expression source, String to) {
        return source.reduce(this, to);
    }

    public void addRate(String from, String to, double rate) {
        rates.put(new Pair(from, to), rate);
    }

    public double getRate(String from, String to) {
        if (from.equals(to)) return 1;
        Double rate = rates.get(new Pair(from, to));
        return rate.doubleValue();
    }
}
