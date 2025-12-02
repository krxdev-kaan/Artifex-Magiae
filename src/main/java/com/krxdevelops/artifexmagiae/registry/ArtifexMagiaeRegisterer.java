package com.krxdevelops.artifexmagiae.registry;

import com.krxdevelops.artifexmagiae.ArtifexMagiae;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

@SuppressWarnings("InstantiationOfUtilityClass")
public class ArtifexMagiaeRegisterer {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(ArtifexMagiae.MODID);
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(ArtifexMagiae.MODID);
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS = DeferredRegister.create(Registries.CREATIVE_MODE_TAB, ArtifexMagiae.MODID);

    public static final ArtifexMagiaeItems _ITEMS_INSTANCE = new ArtifexMagiaeItems();
    public static final ArtifexMagiaeBlocks _BLOCKS_INSTANCE = new ArtifexMagiaeBlocks();
    public static final ArtifexMagiaeCreativeTabs _CREATIVE_TABS_INSTANCE = new ArtifexMagiaeCreativeTabs();

    public static void registerAll(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
