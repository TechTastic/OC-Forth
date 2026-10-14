package io.github.techtastic.ocforth.forth.words.comment;

import io.github.techtastic.ocforth.forth.ForthVM;

public class CommentStartWord implements ForthVM.Word {
    @Override
    public void execute(ForthVM vm) {
        vm.setPreviousState(vm.getState());
        vm.setState(ForthVM.State.COMMENT);
    }
}
