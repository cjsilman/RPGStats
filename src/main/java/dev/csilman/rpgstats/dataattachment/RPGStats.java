package dev.csilman.rpgstats.dataattachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

public record RPGStats(int strength, int constitution, int dexterity, int wisdom, int intelligence, int charisma) {

    public static final Codec<RPGStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT.optionalFieldOf("strength", 1).forGetter(RPGStats::strength),
                    Codec.INT.optionalFieldOf("constitution", 1).forGetter(RPGStats::constitution),
                    Codec.INT.optionalFieldOf("dexterity", 1).forGetter(RPGStats::dexterity),
                    Codec.INT.optionalFieldOf("wisdom", 1).forGetter(RPGStats::wisdom),
                    Codec.INT.optionalFieldOf("intelligence", 1).forGetter(RPGStats::intelligence),
                    Codec.INT.optionalFieldOf("charisma", 1).forGetter(RPGStats::charisma)
            ).apply(instance, RPGStats::new)
    );
}
