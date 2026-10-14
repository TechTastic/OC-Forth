package io.github.techtastic.ocforth.forth.words.logic;

import io.github.techtastic.ocforth.forth.ForthVM;

public class NotBooleanWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        boolean a = vm.pop() != 0;
        vm.push(a ? -1 : 0);
    }
}
