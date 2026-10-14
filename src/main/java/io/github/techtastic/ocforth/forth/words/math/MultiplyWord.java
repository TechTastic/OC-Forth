package io.github.techtastic.ocforth.forth.words.math;

import io.github.techtastic.ocforth.forth.words.MathWord;

public class MultiplyWord extends MathWord {

    @Override
    public long calculate(long a, long b) {
        return a * b;
    }
}
