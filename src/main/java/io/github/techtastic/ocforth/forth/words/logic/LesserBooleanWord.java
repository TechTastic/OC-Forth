package io.github.techtastic.ocforth.forth.words.logic;

import io.github.techtastic.ocforth.forth.words.MathWord;

public class LesserBooleanWord extends MathWord {
    @Override
    public long calculate(long a, long b) {
        return a < b ? -1 : 0;
    }
}
