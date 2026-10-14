package io.github.techtastic.ocforth.forth.words;

import io.github.techtastic.ocforth.forth.ForthVM;

public abstract class MathWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        long b = vm.pop();
        long a = vm.pop();
        vm.push(calculate(a, b));
    }

    public abstract long calculate(long a, long b);
}
