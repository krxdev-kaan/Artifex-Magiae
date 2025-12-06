package com.krxdevelops.artifexmagiae.data;

import com.krxdevelops.artifexmagiae.ArtifexMagiae;
import com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public class AMItemModelProvider extends ItemModelProvider {
    public AMItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, ArtifexMagiae.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        withExistingParent(ArtifexMagiaeItems.WAND_ITEM.getId().toString(), mcLoc("item/generated")).texture("layer0", "item/wand_item");
    }
}
