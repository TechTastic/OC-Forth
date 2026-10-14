package io.github.techtastic.ocforth.forth.words.logic;

import io.github.techtastic.ocforth.forth.words.MathWord;

public class EqualsBooleanWord extends MathWord {
    @Override
    public long calculate(long a, long b) {
        return a == b ? -1 : 0;
    }
}
