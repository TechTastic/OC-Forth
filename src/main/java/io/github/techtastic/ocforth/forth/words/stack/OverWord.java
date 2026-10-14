package io.github.techtastic.ocforth.forth.words.stack;

import io.github.techtastic.ocforth.forth.ForthVM;

public class OverWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        long b = vm.pop();
        long a = vm.pop();
        vm.push(a);
        vm.push(b);
        vm.push(a);
    }
}
