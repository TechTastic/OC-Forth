package io.github.techtastic.ocforth.forth.words.oc;

import io.github.techtastic.ocforth.forth.ForthVM;
import li.cil.oc.api.machine.Machine;

public abstract class MachineWord implements ForthVM.Word {
    public final Machine machine;

    public MachineWord(Machine machine) {
        this.machine = machine;
    }
}
