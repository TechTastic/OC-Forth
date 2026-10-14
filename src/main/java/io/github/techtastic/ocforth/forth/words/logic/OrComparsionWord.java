package io.github.techtastic.ocforth.forth.words.logic;

import io.github.techtastic.ocforth.forth.words.ComparisonWord;

public class OrComparsionWord extends ComparisonWord {
    @Override
    public boolean compare(boolean a, boolean b) {
        return a || b;
    }
}
