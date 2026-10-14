package io.github.techtastic.ocforth.oc.arch;

import io.github.techtastic.ocforth.forth.ForthVM;
import io.github.techtastic.ocforth.forth.words.oc.ByeWord;
import li.cil.oc.api.Driver;
import li.cil.oc.api.driver.item.Memory;
import li.cil.oc.api.machine.Architecture;
import li.cil.oc.api.machine.ExecutionResult;
import li.cil.oc.api.machine.Machine;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.ItemStack;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

import static io.github.techtastic.ocforth.OCForth.MODID;

@Architecture.Name("OC-Forth")
@Architecture.NoMemoryRequirements
public class ForthArchitecture implements Architecture {
    private final Machine machine;

    private ForthVM vm;
    private final Deque<String> file = new ArrayDeque<>();

    public ForthArchitecture(Machine machine) {
        this.machine = machine;
    }

    private String loadFile(String path) {
        ResourceLocation loc = ResourceLocation.fromNamespaceAndPath(MODID, path);
        Optional<Resource> resource = this.machine.host().getEnvironmentLevel().getServer().getResourceManager().getResource(loc);
        if (resource.isPresent()) {
            try (BufferedReader reader = resource.get().openAsReader()) {
                return String.join("\n", reader.lines().toList());
            } catch (IOException e) {
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean isInitialized() {
        return this.vm != null;
    }

    @Override
    public boolean recomputeMemory(Iterable<ItemStack> components) {
        double memory = 0;
        for (ItemStack stack : components) {
            if (Driver.driverFor(stack) instanceof Memory mem)
                memory += Math.min(3072, mem.amount(stack));
        }
        if (this.vm != null)
            this.vm = this.vm.copy((int) memory);
        else
            this.vm = new ForthVM((int) memory);
        return memory > 0;
    }

    @Override
    public boolean initialize() {
        boolean hasMemory = this.recomputeMemory(this.machine.host().internalComponents());

        this.vm.addWord("bye", new ByeWord(this.machine));

        if (this.loadFile("init.fs") instanceof String script)
            this.file.addAll(Arrays.asList(script.trim().split("\\s+")));

        return hasMemory && !this.file.isEmpty();
    }

    @Override
    public void close() {
        this.vm = null;
        this.file.clear();
    }

    @Override
    public void runSynchronized() {}

    @Override
    public ExecutionResult runThreaded(boolean isSynchronizedReturn) {
        if (this.file.poll() instanceof String token) {
            try {
                this.vm.execute(token);
                return new ExecutionResult.Sleep(1);
            } catch (RuntimeException e) {
                return new ExecutionResult.Error(e.getMessage());
            }
        }
        return new ExecutionResult.Shutdown(false);
    }

    @Override
    public void onSignal() {}

    @Override
    public void onConnect() {}

    @Override
    public void loadData(CompoundTag nbt) {
        //if (nbt.contains("forth", Tag.TAG_COMPOUND))
        //    this.vm.load(nbt.getCompound("forth"));
    }

    @Override
    public void saveData(CompoundTag nbt) {
        //CompoundTag tag = new CompoundTag();
        //nbt.put("forth", tag);
        //this.vm.save(tag);
    }
}
