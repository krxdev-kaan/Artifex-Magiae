package com.krxdevelops.artifexmagiae;

import com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeRegisterer;
import org.slf4j.Logger;

import com.mojang.logging.LogUtils;

import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;

@Mod(ArtifexMagiae.MODID)
public class ArtifexMagiae {
    public static final String MODID = "artifexmagiae";
    public static final Logger LOGGER = LogUtils.getLogger();

    public ArtifexMagiae(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ArtifexMagiaeRegisterer.registerAll(modEventBus);
        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(FMLCommonSetupEvent event) {}
}
