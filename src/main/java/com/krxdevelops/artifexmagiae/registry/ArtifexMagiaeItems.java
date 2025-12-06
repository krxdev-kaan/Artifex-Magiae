package com.krxdevelops.artifexmagiae.registry;

import com.krxdevelops.artifexmagiae.ArtifexMagiae;
import com.krxdevelops.artifexmagiae.item.WandItem;
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

    public static final DeferredItem<Item> WAND_ITEM = ITEMS.register(
            "wand_item",
            (name) -> new WandItem(name.getPath(), new Item.Properties()));

    public ArtifexMagiaeItems() {
        ArtifexMagiae.LOGGER.info("ArtifexMagiaeItems instantiated.");
    }
}
