package dev.csilman.rpgstats.dataattachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RPGLeveling(double xp, int level, int attributePoints, int milestonePoints) {

    public static final Codec<RPGLeveling> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.DOUBLE.optionalFieldOf("xp", 0.0d).forGetter(RPGLeveling::xp),
                    Codec.INT.optionalFieldOf("level", 0).forGetter(RPGLeveling::level),
                    Codec.INT.optionalFieldOf("attributepoints", 0).forGetter(RPGLeveling::attributePoints),
                    Codec.INT.optionalFieldOf("milestoneattributes", 0).forGetter(RPGLeveling::milestonePoints)
            ).apply(instance, RPGLeveling::new)
    );
}
