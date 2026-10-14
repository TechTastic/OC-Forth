package io.github.techtastic.ocforth.forth.words;

import io.github.techtastic.ocforth.forth.ForthVM;

public class LiteralWord implements ForthVM.Word {
    private final long x;

    public LiteralWord(long x) {
        this.x = x;
    }

    @Override
    public void execute(ForthVM vm) {
        vm.push(this.x);
    }
}
