package io.github.techtastic.ocforth;

import io.github.techtastic.ocforth.oc.arch.ForthArchitecture;
import li.cil.oc.api.Machine;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.ModContainer;

@Mod(OCForth.MODID)
public class OCForth {
    public static final String MODID = "ocforth";
    public static final Logger LOGGER = LogUtils.getLogger();

    public OCForth(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);
    }

    private void commonSetup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Machine.add(ForthArchitecture.class);
        });
    }
}
