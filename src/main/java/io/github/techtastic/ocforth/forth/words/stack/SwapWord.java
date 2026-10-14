package io.github.techtastic.ocforth.forth.words.stack;

import io.github.techtastic.ocforth.forth.ForthVM;

public class SwapWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        long b = vm.pop();
        long a = vm.pop();
        vm.push(b);
        vm.push(a);
    }
}
