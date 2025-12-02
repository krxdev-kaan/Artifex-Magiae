package com.krxdevelops.artifexmagiae.registry;

import com.krxdevelops.artifexmagiae.ArtifexMagiae;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredItem;

import static com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeRegisterer.ITEMS;
import static com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeRegisterer.BLOCKS;

public class ArtifexMagiaeBlocks {
    // Block registries
    public static final DeferredBlock<Block> EXAMPLE_BLOCK = BLOCKS.registerSimpleBlock("example_block", BlockBehaviour.Properties.of().mapColor(MapColor.STONE));

    // BlockItem registries
    public static final DeferredItem<BlockItem> EXAMPLE_BLOCK_ITEM = ITEMS.registerSimpleBlockItem("example_block", EXAMPLE_BLOCK);

    public ArtifexMagiaeBlocks() {
        ArtifexMagiae.LOGGER.info("ArtifexMagiaeBlocks instantiated.");
    }
}
