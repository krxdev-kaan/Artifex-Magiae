package com.krxdevelops.artifexmagiae.registry;

import com.krxdevelops.artifexmagiae.ArtifexMagiae;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.CreativeModeTabs;
import net.neoforged.neoforge.registries.DeferredHolder;

import static com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeRegisterer.CREATIVE_MODE_TABS;

public class ArtifexMagiaeCreativeTabs {
    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXAMPLE_TAB = CREATIVE_MODE_TABS.register(
        "example_tab",
        () -> CreativeModeTab.builder()
            .title(Component.translatable("itemGroup.artifexmagiae"))
            .withTabsBefore(CreativeModeTabs.COMBAT)
            .icon(() -> ArtifexMagiaeItems.EXAMPLE_ITEM.get().getDefaultInstance())
            .displayItems((parameters, output) -> {
                output.accept(ArtifexMagiaeItems.EXAMPLE_ITEM.get());
            })
            .build());

    public ArtifexMagiaeCreativeTabs() {
        ArtifexMagiae.LOGGER.info("ArtifexMagiaeCreativeTabs instantiated.");
    }
}
