package io.github.techtastic.ocforth.forth.words.comment;

import io.github.techtastic.ocforth.forth.ForthVM;

public class CommentEndWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        vm.setState(vm.getPreviousState());
    }
}
