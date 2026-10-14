package io.github.techtastic.ocforth.forth.words;

import io.github.techtastic.ocforth.forth.ForthVM;

public abstract class ComparisonWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        boolean b = vm.pop() != 0;
        boolean a = vm.pop() != 0;
        vm.push(compare(a, b) ? -1 : 0);
    }

    public abstract boolean compare(boolean a, boolean b);
}
