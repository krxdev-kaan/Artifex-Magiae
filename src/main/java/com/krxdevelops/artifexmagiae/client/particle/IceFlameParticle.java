package com.krxdevelops.artifexmagiae.client.particle;

import com.krxdevelops.artifexmagiae.client.particle.options.SpellParticleOptions;
import com.krxdevelops.artifexmagiae.core.Path;
import com.krxdevelops.artifexmagiae.core.PathType;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class IceFlameParticle extends TextureSheetParticle {
    private final Path particlePath;
    private final @Nullable Entity parentEntity;

    protected IceFlameParticle(SpellParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.friction = 0;
        this.particlePath = options.getPath();
        this.lifetime = (int)(options.getPath().duration());
        if (options.getPath().circularPathParams().reference() == null)
            parentEntity = level.getEntity(options.getPath().circularPathParams().referenceEntityID());
        else
            parentEntity = null;
    }

    @Override
    public @NotNull ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    public void tick() {
        this.xo = this.x;
        this.yo = this.y;
        this.zo = this.z;

        Vec3 newPos = this.particlePath.getPosition(this.age, this.parentEntity != null ? this.parentEntity.position() : null);

        if (this.age++ >= this.lifetime)
            this.remove();
        else {
            this.setPos(newPos.x, newPos.y, newPos.z);
        }
    }

    @Override
    public float getQuadSize(float scaleFactor) {
        float f = ((float)this.age + scaleFactor) / (float)this.lifetime;
        return this.quadSize * (1.0F - f * f * 0.5F);
    }

    @Override
    public int getLightColor(float partialTick) {
        float f = ((float)this.age + partialTick) / (float)this.lifetime;
        f = Mth.clamp(f, 0.0F, 1.0F);
        int i = super.getLightColor(partialTick);
        int j = i & 0xFF;
        int k = i >> 16 & 0xFF;
        j += (int)(f * 15.0F * 16.0F);
        if (j > 240) {
            j = 240;
        }

        return j | k << 16;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Provider implements ParticleProvider<SpellParticleOptions> {
        private final SpriteSet sprite;

        public Provider(SpriteSet sprites) {
            this.sprite = sprites;
        }

        public Particle createParticle(
                @NotNull SpellParticleOptions spellParticleOptions,
                @NotNull ClientLevel level,
                double x,
                double y,
                double z,
                double xSpeed,
                double ySpeed,
                double zSpeed
        ) {
            IceFlameParticle iceFlameParticle = new IceFlameParticle(spellParticleOptions, level, x, y, z, xSpeed, ySpeed, zSpeed);
            iceFlameParticle.pickSprite(this.sprite);
            return iceFlameParticle;
        }
    }


}
