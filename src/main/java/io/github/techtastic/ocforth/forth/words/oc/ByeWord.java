package io.github.techtastic.ocforth.forth.words.oc;

import io.github.techtastic.ocforth.forth.ForthVM;
import io.github.techtastic.ocforth.forth.words.MachineWord;
import li.cil.oc.api.machine.Machine;

public class ByeWord extends MachineWord {
    public ByeWord(Machine machine) {
        super(machine);
    }

    @Override
    public void execute(ForthVM vm) {
        this.machine.stop();
    }

    @Override
    public boolean immediate() {
        return true;
    }
}
