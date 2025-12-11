package com.krxdevelops.artifexmagiae.core;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.network.codec.NeoForgeStreamCodecs;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.util.List;
import java.util.Optional;

@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public record Path (PathType pathType, PathFollowType followType, double duration,
                    CircularPathParams circularPathParams, LinearPathParams linearPathParams, LinearSmoothTransitiveParams linearSmoothTransitiveParams) {
    private Path(int pathTypeOrd, int followTypeOrd, double duration,
                 Optional<CircularPathParams> cpParams, Optional<LinearPathParams> lpParams, Optional<LinearSmoothTransitiveParams> lstParams) {
        this(PathType.values()[pathTypeOrd], PathFollowType.values()[followTypeOrd], duration,
             cpParams.orElse(null), lpParams.orElse(null), lstParams.orElse(null));
    }

    public int getPathType() {
        return pathType.ordinal();
    }

    public int getPathFollowType() {
        return followType.ordinal();
    }

    public Vec3 getPosition(double ticksPassed, @Nullable final Vec3 referenceEntityPos) {
        switch (pathType) {
            case CIRCULAR:
                double angle = this.circularPathParams.initialAngle + this.circularPathParams.angularSpeed * ticksPassed;
                double distance = this.circularPathParams.initialDistance + this.circularPathParams.circularGrowthRate * ticksPassed;
                return (this.circularPathParams.reference != null ? this.circularPathParams.reference : referenceEntityPos).add(
                    Mth.cos((float)angle) * distance,
                    0.33 + this.circularPathParams.floatingSpeed * ticksPassed,
                    Mth.sin((float)angle) * distance
                );
            case LINEAR:
                return new Vec3(0, 0, 0);
            case LINEAR_SMOOTH_TRANSITIVE:
                return new Vec3(0, 0, 0);
        }

        return new Vec3(0, 0, 0);
    }

    public static Path createCircular(double duration, Vec3 reference, double initialAngle, double initialDistance, double angularSpeed, double floatingSpeed, double circularGrowthRate) {
        return new Path(PathType.CIRCULAR, PathFollowType.NOT_APPLICABLE, duration,
                        new CircularPathParams(reference, null, initialAngle, initialDistance, angularSpeed, floatingSpeed, circularGrowthRate), null, null);
    }

    public static Path createCircular(double duration, int referenceEntityID, double initialAngle, double initialDistance, double angularSpeed, double floatingSpeed, double circularGrowthRate) {
        return new Path(PathType.CIRCULAR, PathFollowType.NOT_APPLICABLE, duration,
                        new CircularPathParams(Optional.empty(), referenceEntityID, initialAngle, initialDistance, angularSpeed, floatingSpeed, circularGrowthRate), null, null);
    }

    public static Path createLinear(double duration, Vec3 startPoint, Vec3 endPoint, double speed) {
        return new Path(PathType.LINEAR, PathFollowType.NOT_APPLICABLE, duration,
                        null, new LinearPathParams(startPoint, endPoint, speed), null);
    }

    public static Path createLinearSmoothTransitive(double duration, Vec3 startPoint, Vec3 endPoint, Vec3 nextEndPoint, double speed) {
        return new Path(PathType.LINEAR_SMOOTH_TRANSITIVE, PathFollowType.NOT_APPLICABLE, duration,
                        null, null, new LinearSmoothTransitiveParams(startPoint, endPoint, speed, nextEndPoint));
    }

    public static final Codec<Path> CODEC = RecordCodecBuilder.create(instance ->
        instance.group(
            Codec.INT.fieldOf("pathType").forGetter(Path::getPathType),
            Codec.INT.fieldOf("pathFollowType").forGetter(Path::getPathFollowType),
            Codec.DOUBLE.fieldOf("duration").forGetter(Path::duration),
            CircularPathParams.CODEC.optionalFieldOf("circularPathParams").forGetter(p -> Optional.ofNullable(p.circularPathParams())),
            LinearPathParams.CODEC.optionalFieldOf("linearPathParams").forGetter(p -> Optional.ofNullable(p.linearPathParams())),
            LinearSmoothTransitiveParams.CODEC.optionalFieldOf("linearSmoothTransitiveParams").forGetter(p -> Optional.ofNullable(p.linearSmoothTransitiveParams()))
        ).apply(instance, Path::new)
    );

    public static final StreamCodec<ByteBuf, Path> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.INT, Path::getPathType,
            ByteBufCodecs.INT, Path::getPathFollowType,
            ByteBufCodecs.DOUBLE, Path::duration,
            CircularPathParams.STREAM_CODEC.apply(ByteBufCodecs::optional), path -> Optional.ofNullable(path.circularPathParams()),
            LinearPathParams.STREAM_CODEC.apply(ByteBufCodecs::optional), path -> Optional.ofNullable(path.linearPathParams()),
            LinearSmoothTransitiveParams.STREAM_CODEC.apply(ByteBufCodecs::optional), path -> Optional.ofNullable(path.linearSmoothTransitiveParams()),
            Path::new
    );

    public record CircularPathParams(Vec3 reference, Integer referenceEntityID, Double initialAngle, Double initialDistance, Double angularSpeed, Double floatingSpeed, Double circularGrowthRate) {
        public CircularPathParams(Optional<Vector3f> reference, Integer referenceEntityID, Double initialAngle, Double initialDistance, Double angularSpeed, Double floatingSpeed, Double circularGrowthRate) {
            this(reference.map(Vec3::new).orElse(null), referenceEntityID, initialAngle, initialDistance, angularSpeed, floatingSpeed, circularGrowthRate);
        }

        public static Codec<CircularPathParams> CODEC = RecordCodecBuilder.create(instance -> instance.group(
            Vec3.CODEC.fieldOf("reference").forGetter(CircularPathParams::reference),
            Codec.INT.fieldOf("referenceEntityID").forGetter(CircularPathParams::referenceEntityID),
            Codec.DOUBLE.fieldOf("initialAngle").forGetter(CircularPathParams::initialAngle),
            Codec.DOUBLE.fieldOf("initialDistance").forGetter(CircularPathParams::initialDistance),
            Codec.DOUBLE.fieldOf("angularSpeed").forGetter(CircularPathParams::angularSpeed),
            Codec.DOUBLE.fieldOf("floatingSpeed").forGetter(CircularPathParams::floatingSpeed),
            Codec.DOUBLE.fieldOf("circularGrowthRate").forGetter(CircularPathParams::circularGrowthRate)
            ).apply(instance, CircularPathParams::new)
        );

        public static StreamCodec<ByteBuf, CircularPathParams> STREAM_CODEC = NeoForgeStreamCodecs.composite(
            ByteBufCodecs.VECTOR3F.apply(ByteBufCodecs::optional), p -> (Optional.ofNullable(p.reference != null ? p.reference.toVector3f() : null)),
            ByteBufCodecs.INT, CircularPathParams::referenceEntityID,
            ByteBufCodecs.DOUBLE, CircularPathParams::initialAngle,
            ByteBufCodecs.DOUBLE, CircularPathParams::initialDistance,
            ByteBufCodecs.DOUBLE, CircularPathParams::angularSpeed,
            ByteBufCodecs.DOUBLE, CircularPathParams::floatingSpeed,
            ByteBufCodecs.DOUBLE, CircularPathParams::circularGrowthRate,
            CircularPathParams::new
        );
    }

    public record LinearPathParams(Vec3 startPoint, Vec3 endPoint, Double speed) {
        public LinearPathParams(Vector3f startPoint, Vector3f endPoint, Double speed) {
            this(new Vec3(startPoint), new Vec3(endPoint), speed);
        }

        public static Codec<LinearPathParams> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Vec3.CODEC.fieldOf("startPoint").forGetter(LinearPathParams::startPoint),
                    Vec3.CODEC.fieldOf("endPoint").forGetter(LinearPathParams::endPoint),
                    Codec.DOUBLE.fieldOf("speed").forGetter(LinearPathParams::speed)
            ).apply(instance, LinearPathParams::new)
        );

        public static StreamCodec<ByteBuf, LinearPathParams> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, p -> p.startPoint.toVector3f(),
            ByteBufCodecs.VECTOR3F, p -> p.endPoint.toVector3f(),
            ByteBufCodecs.DOUBLE, LinearPathParams::speed,
            LinearPathParams::new
        );
    }

    public record LinearSmoothTransitiveParams(Vec3 startPoint, Vec3 endPoint, Double speed, Vec3 nextPoint) {
        public LinearSmoothTransitiveParams(Vector3f startPoint, Vector3f endPoint, Double speed, Vector3f nextPoint) {
            this(new Vec3(startPoint), new Vec3(endPoint), speed, new Vec3(nextPoint));
        }

        public static Codec<LinearSmoothTransitiveParams> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Vec3.CODEC.fieldOf("startPoint").forGetter(LinearSmoothTransitiveParams::startPoint),
                Vec3.CODEC.fieldOf("endPoint").forGetter(LinearSmoothTransitiveParams::endPoint),
                Codec.DOUBLE.fieldOf("speed").forGetter(LinearSmoothTransitiveParams::speed),
                Vec3.CODEC.fieldOf("nextPoint").forGetter(LinearSmoothTransitiveParams::nextPoint)
            ).apply(instance, LinearSmoothTransitiveParams::new)
        );

        public static StreamCodec<ByteBuf, LinearSmoothTransitiveParams> STREAM_CODEC = StreamCodec.composite(
            ByteBufCodecs.VECTOR3F, p -> p.startPoint.toVector3f(),
            ByteBufCodecs.VECTOR3F, p -> p.endPoint.toVector3f(),
            ByteBufCodecs.DOUBLE, LinearSmoothTransitiveParams::speed,
            ByteBufCodecs.VECTOR3F, p -> p.nextPoint.toVector3f(),
            LinearSmoothTransitiveParams::new
        );
    }
}
