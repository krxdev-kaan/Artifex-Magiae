package com.krxdevelops.artifexmagiae.item;

import com.krxdevelops.artifexmagiae.client.particle.options.SpellParticleOptions;
import com.krxdevelops.artifexmagiae.core.Path;
import com.krxdevelops.artifexmagiae.registry.ArtifexMagiaeParticleTypes;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WandItem extends AMBaseItem {
    public WandItem(String name, Properties props) {
        super(name, props);
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(@NotNull Level level, Player player, @NotNull InteractionHand usedHand)
    {
        ItemStack itemstack = player.getItemInHand(usedHand);
        player.startUsingItem(usedHand);
        return InteractionResultHolder.success(itemstack);
    }

    @Override
    public void onUseTick(@NotNull Level level, @NotNull LivingEntity livingEntity, @NotNull ItemStack stack, int remainingUseDuration) {
        int passedTick = Integer.MAX_VALUE - remainingUseDuration;
        if (level.isClientSide()) {
            /*if (passedTick % 8 == 0) {
                for (int i = 0; i < 32; i++) {
                    float deg = (Mth.RAD_TO_DEG * i * 11.25f) + (passedTick % 16 == 0 ? (Mth.RAD_TO_DEG * 5.625f) : 0);
                    for (int k = 0; k < 1; k++) {
                        SpellParticleOptions options = new SpellParticleOptions(
                                ArtifexMagiaeParticleTypes.TEST_PARTICLE.get(),
                                Path.createCircular(10, livingEntity.getId(), deg, 1, 0.15, 0.05, 0.1)
                        );
                        level.addParticle(
                                options,
                                livingEntity.getX() + Mth.cos(deg),
                                livingEntity.getY() + 0.25,
                                livingEntity.getZ() + Mth.sin(deg),
                                0.15,
                                0.05,
                                0.1
                        );
                    }
                }
            }*/
            for (int i = 0; i < (passedTick % 8 == 0 ? 32 : 4); i++) {
                float deg = (Mth.RAD_TO_DEG * i * (passedTick % 8 == 0 ? 11.25f : 90.0f)) + (((Mth.PI * 0.5f) / 8) * (passedTick % 8));
                for (int k = 0; k < 1; k++) {
                    SpellParticleOptions options = new SpellParticleOptions(
                            ArtifexMagiaeParticleTypes.TEST_PARTICLE.get(),
                            Path.createCircular(20, livingEntity.getId(), deg, 1, 0.1, 0.08, 0.1)
                    );
                    level.addParticle(
                            options,
                            livingEntity.getX() + Mth.cos(deg),
                            livingEntity.getY() + 0.25,
                            livingEntity.getZ() + Mth.sin(deg),
                            0.15,
                            0.05,
                            0.1
                    );
                }
            }
        }
        else {
            double currentRadius = Mth.clamp(1.0 + passedTick * 0.055, 1, 3);
            AABB aoeRange = livingEntity.getBoundingBox().inflate(currentRadius);
            List<Entity> affectedEntities = level.getEntities(livingEntity, aoeRange, (e) -> e.getBoundingBox().distanceToSqr(livingEntity.position()) <= currentRadius*currentRadius);
            affectedEntities.forEach(e -> {
                e.igniteForSeconds(2);
                if (passedTick % 10 == 0)
                    e.hurt(livingEntity.damageSources().inFire(), 1);
            });
        }
    }

    @Override
    public int getUseDuration(@NotNull ItemStack stack, @NotNull LivingEntity entity) {
        return Integer.MAX_VALUE;
    }
}
