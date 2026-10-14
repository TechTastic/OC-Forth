package io.github.techtastic.ocforth.forth.words.math;

import io.github.techtastic.ocforth.forth.words.MathWord;

public class SubtractWord extends MathWord {

    @Override
    public long calculate(long a, long b) {
        return a - b;
    }
}
