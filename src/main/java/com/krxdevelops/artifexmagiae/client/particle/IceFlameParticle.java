package com.krxdevelops.artifexmagiae.client.particle;

import com.krxdevelops.artifexmagiae.client.particle.options.SpellParticleOptions;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.*;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@OnlyIn(Dist.CLIENT)
public class IceFlameParticle extends TextureSheetParticle {
    private final Entity parentEntity;
    private final double angularSpeed;
    private final double floatingSpeed;
    private final double circularGrowthSpeed;
    private double lastAngle;
    private double lastDistance;

    protected IceFlameParticle(SpellParticleOptions options, ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(level, x, y, z, xSpeed, ySpeed, zSpeed);
        this.friction = 0;
        this.angularSpeed = options.getPath().circularPathParams().angularSpeed();
        this.floatingSpeed = options.getPath().circularPathParams().floatingSpeed();
        this.circularGrowthSpeed = options.getPath().circularPathParams().circularGrowthRate();
        this.lifetime = (int)(options.getPath().duration());
        parentEntity = level.getEntity(options.getPath().circularPathParams().referenceEntityID());
        lastAngle = options.getPath().circularPathParams().initialAngle();
        lastDistance = options.getPath().circularPathParams().initialDistance();
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

        double parentDistanceY = parentEntity.getY() - this.y;
        double newAngle = this.lastAngle += angularSpeed;
        double newDistance = this.lastDistance += circularGrowthSpeed;

        Vec3 displacementVector = new Vec3(
                Mth.cos((float)newAngle) * newDistance,
                -parentDistanceY + this.floatingSpeed,
                Mth.sin((float)newAngle) * newDistance
        );

        if (this.age++ >= this.lifetime)
            this.remove();
        else {
            this.setPos(
                    parentEntity.getX() + displacementVector.x,
                    parentEntity.getY() + displacementVector.y,
                    parentEntity.getZ() + displacementVector.z
            );
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
