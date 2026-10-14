package io.github.techtastic.ocforth.forth.words.math;

import io.github.techtastic.ocforth.forth.words.MathWord;

public class ModWord extends MathWord {

    @Override
    public long calculate(long a, long b) {
        if (b == 0)
            throw new RuntimeException("division by zero");
        return Math.floorMod(a, b);
    }
}
