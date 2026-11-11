package dev.csilman.rpgstats.dataattachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.csilman.rpgstats.util.RPGStatsEnum;

import java.util.EnumMap;
import java.util.Map;

public record RPGStats(Map<RPGStatsEnum, Integer> stats) {

    public static final Map<RPGStatsEnum, Integer> DEFAULT = new EnumMap<>(Map.of(
            RPGStatsEnum.Strength, 1,
            RPGStatsEnum.Constitution, 1,
            RPGStatsEnum.Dexterity, 1,
            RPGStatsEnum.Wisdom, 1,
            RPGStatsEnum.Intelligence, 1,
            RPGStatsEnum.Charisma, 1)
    );

    public RPGStats() {
        this(DEFAULT);
    }

    public static final Codec<RPGStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(RPGStatsEnum.CODEC, Codec.INT)
                            .optionalFieldOf("stats", DEFAULT)
                            .forGetter(RPGStats::stats)
            ).apply(instance, RPGStats::new)
    );

//    public static final Codec<RPGStats> CODEC = Codec.unboundedMap(RPGStatsEnum.CODEC, Codec.INT)
//            .xmap(RPGStats::new, RPGStats::stats);

    public int getRPGStat(RPGStatsEnum statType) {
        return stats.get(statType);
    }

    public RPGStats updateRPGStat(RPGStatsEnum statType, int value) {
        // updateRPGStat is called on a current players stats, so assumption is you can just use stats here
        Map<RPGStatsEnum, Integer> newStats = new EnumMap<>(stats);
        newStats.put(statType, value);

        return new RPGStats(newStats);
    }
}
