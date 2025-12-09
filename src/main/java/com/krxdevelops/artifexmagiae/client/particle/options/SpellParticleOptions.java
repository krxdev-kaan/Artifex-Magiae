package com.krxdevelops.artifexmagiae.client.particle.options;

import com.krxdevelops.artifexmagiae.core.Path;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.particles.ParticleOptions;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import org.jetbrains.annotations.NotNull;

public class SpellParticleOptions implements ParticleOptions {
    private final ParticleType<SpellParticleOptions> type;
    private final Path path;

    public SpellParticleOptions(ParticleType<SpellParticleOptions> type, Path path) {
        this.type = type;
        this.path = path;
    }

    @Override
    public @NotNull ParticleType<SpellParticleOptions> getType() {
        return this.type;
    }

    public Path getPath() {
        return this.path;
    }

    public static SpellParticleOptions create(ParticleType<SpellParticleOptions> type, Path path) {
        return new SpellParticleOptions(type, path);
    }

    public static @NotNull MapCodec<SpellParticleOptions> codec(ParticleType<SpellParticleOptions> type) {
        return Path.CODEC.xmap(p -> new SpellParticleOptions(type, p), spo -> spo.path).fieldOf("path");
    }

    public static @NotNull StreamCodec<? super ByteBuf, SpellParticleOptions> streamCodec(ParticleType<SpellParticleOptions> type) {
        return Path.STREAM_CODEC.map(p -> new SpellParticleOptions(type, p), spo -> spo.path);
    }
}
