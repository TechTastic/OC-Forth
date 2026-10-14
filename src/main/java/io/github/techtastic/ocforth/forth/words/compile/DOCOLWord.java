package io.github.techtastic.ocforth.forth.words.compile;

import io.github.techtastic.ocforth.forth.ForthVM;

public class DOCOLWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        vm.setPreviousState(vm.getState());
        vm.setState(ForthVM.State.COMPILE);
    }
}
