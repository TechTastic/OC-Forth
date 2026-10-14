package io.github.techtastic.ocforth.forth.words.compile;

import io.github.techtastic.ocforth.forth.ForthVM;

import java.util.List;

public class CompiledWord implements ForthVM.Word {
    private final List<ForthVM.Word> body;

    public CompiledWord(List<ForthVM.Word> body) {
        this.body = body;
    }

    @Override
    public void execute(ForthVM vm) {
        for (ForthVM.Word word : this.body) {
            word.execute(vm);
        }
    }
}
