package io.github.techtastic.ocforth.forth.words.compile;

import io.github.techtastic.ocforth.forth.ForthVM;

import java.util.List;

public class CompiledWord implements ForthVM.Word {
    private final List<String> body;

    public CompiledWord(List<String> body) {
        this.body = body;
    }

    @Override
    public void execute(ForthVM vm) {
        for (String word : this.body) {
            vm.execute(word);
        }
    }
}
