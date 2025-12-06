package com.krxdevelops.artifexmagiae.client.particle.options;

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
    private final int parentEntityID;

    public SpellParticleOptions(ParticleType<SpellParticleOptions> type, int id) {
        this.type = type;
        this.parentEntityID = id;
    }

    @Override
    public @NotNull ParticleType<SpellParticleOptions> getType() {
        return this.type;
    }

    public int getEntityID() {
        return this.parentEntityID;
    }

    public static SpellParticleOptions create(ParticleType<SpellParticleOptions> type, int id) {
        return new SpellParticleOptions(type, id);
    }

    public static @NotNull MapCodec<SpellParticleOptions> codec(ParticleType<SpellParticleOptions> type) {
        return Codec.INT.xmap(id -> new SpellParticleOptions(type, id), spo -> spo.parentEntityID).fieldOf("parentEntityID");
    }

    public static @NotNull StreamCodec<? super RegistryFriendlyByteBuf, SpellParticleOptions> streamCodec(ParticleType<SpellParticleOptions> type) {
        return ByteBufCodecs.INT.map(id -> new SpellParticleOptions(type, id), spo -> spo.parentEntityID);
    }
}
