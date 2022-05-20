package com.elevenhelevenm.practice.board.domain.money;

import java.util.Objects;

public class Pair {

    private String from;
    private String to;

    public Pair(String from, String to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pair pair = (Pair) o;
        return from.equals(pair.from) && to.equals(pair.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
