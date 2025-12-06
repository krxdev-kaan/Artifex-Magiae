package com.krxdevelops.artifexmagiae.registry;

import com.krxdevelops.artifexmagiae.ArtifexMagiae;
import com.krxdevelops.artifexmagiae.client.particle.options.SpellParticleOptions;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.neoforged.neoforge.registries.DeferredHolder;
import org.jetbrains.annotations.NotNull;

import static com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeRegisterer.PARTICLE_TYPES;

public class ArtifexMagiaeParticleTypes {
    public static final DeferredHolder<ParticleType<?>, ParticleType<SpellParticleOptions>> TEST_PARTICLE = PARTICLE_TYPES.register(
            "test_particle",
            (name) -> new ParticleType<SpellParticleOptions>(true) {
                @Override
                public @NotNull MapCodec<SpellParticleOptions> codec() {
                    return SpellParticleOptions.codec(this);
                }

                @Override
                public @NotNull StreamCodec<? super RegistryFriendlyByteBuf, SpellParticleOptions> streamCodec() {
                    return SpellParticleOptions.streamCodec(this);
                }
            });

    public ArtifexMagiaeParticleTypes() {
        ArtifexMagiae.LOGGER.info("ArtifexMagiaeParticleTypes instantiated.");
    }
}
