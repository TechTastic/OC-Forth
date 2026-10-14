package io.github.techtastic.ocforth.forth.words.temp;

import io.github.techtastic.ocforth.forth.ForthVM;

public class PrintWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        System.out.println(vm.pop());
    }
}
