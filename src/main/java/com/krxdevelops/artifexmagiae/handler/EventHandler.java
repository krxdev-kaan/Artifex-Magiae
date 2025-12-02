package com.krxdevelops.artifexmagiae.handler;

import com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeBlocks;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ArtifexMagiaeBlocks.EXAMPLE_BLOCK_ITEM);
        }
    }
}
