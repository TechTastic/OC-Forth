package io.github.techtastic.ocforth.forth.words.compile;

import io.github.techtastic.ocforth.forth.ForthVM;

public class DOCOLEndWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        vm.addWord(vm.getCurrentDefinition(), new CompiledWord(vm.getCurrentDefinitionBody()));
        vm.clearCurrentDefinition();
        vm.setPreviousState(vm.getState());
        vm.setState(ForthVM.State.INTERPRET);
    }
}
