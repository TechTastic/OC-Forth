package io.github.techtastic.ocforth.forth.words.stack;

import io.github.techtastic.ocforth.forth.ForthVM;

public class DuplicateWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        long a = vm.pop();
        vm.push(a);
        vm.push(a);
    }
}
