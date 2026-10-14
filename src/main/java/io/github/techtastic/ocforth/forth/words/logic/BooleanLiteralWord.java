package io.github.techtastic.ocforth.forth.words.logic;

import io.github.techtastic.ocforth.forth.words.LiteralWord;

public class BooleanLiteralWord extends LiteralWord {
    public BooleanLiteralWord(boolean bool) {
        super(bool ? -1 : 0);
    }
}
