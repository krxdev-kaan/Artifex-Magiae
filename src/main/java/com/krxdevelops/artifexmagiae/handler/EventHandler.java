package com.krxdevelops.artifexmagiae.handler;

import com.krxdevelops.artifexmagiae.client.particle.IceFlameParticle;
import com.krxdevelops.artifexmagiae.data.AMItemModelProvider;
import com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeBlocks;
import com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeParticleTypes;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterParticleProvidersEvent;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;

@EventBusSubscriber
public class EventHandler {
    @SubscribeEvent
    public static void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(ArtifexMagiaeBlocks.EXAMPLE_BLOCK_ITEM);
        }
    }

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        PackOutput output = generator.getPackOutput();
        ExistingFileHelper existingFileHelper = event.getExistingFileHelper();

        generator.addProvider(
                event.includeClient(),
                new AMItemModelProvider(output, existingFileHelper)
        );
    }

    @SubscribeEvent
    public static void registerParticleProvider(RegisterParticleProvidersEvent event) {
        event.registerSpriteSet(ArtifexMagiaeParticleTypes.TEST_PARTICLE.get(), IceFlameParticle.Provider::new);
    }
}
