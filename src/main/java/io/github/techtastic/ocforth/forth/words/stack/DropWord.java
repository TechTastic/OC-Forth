package io.github.techtastic.ocforth.forth.words.stack;

import io.github.techtastic.ocforth.forth.ForthVM;

public class DropWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        vm.pop();
    }
}
