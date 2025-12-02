package com.krxdevelops.artifexmagiae.registry;

import com.krxdevelops.artifexmagiae.ArtifexMagiae;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeRegisterer.ITEMS;

public class ArtifexMagiaeItems {
    public static final DeferredItem<Item> EXAMPLE_ITEM = ITEMS.registerSimpleItem(
            "example_item",
            new Item.Properties().food(new FoodProperties.Builder()
                    .alwaysEdible()
                    .nutrition(1)
                    .saturationModifier(2f)
                    .build()
            ));

    public ArtifexMagiaeItems() {
        ArtifexMagiae.LOGGER.info("ArtifexMagiaeItems instantiated.");
    }
}
