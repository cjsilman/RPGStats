package dev.csilman.rpgstats.dataattachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.csilman.rpgstats.util.RPGStatsEnum;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;

import java.util.Collections;
import java.util.EnumMap;
import java.util.Map;

public record RPGStats(Map<RPGStatsEnum, Integer> stats) {

    public static final Codec<RPGStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.unboundedMap(RPGStatsEnum.CODEC, Codec.INT)
                            .optionalFieldOf("stats", Map.of())
                            .forGetter(RPGStats::stats)
            ).apply(instance, RPGStats::new)
    );

    public static final StreamCodec<ByteBuf, Map<RPGStatsEnum, Integer>> STREAM_CODEC =
    ByteBufCodecs.map(
            size -> new EnumMap<>(RPGStatsEnum.class),
            RPGStatsEnum.STREAM_CODEC,
            ByteBufCodecs.VAR_INT
    );

    public RPGStats {
        EnumMap<RPGStatsEnum, Integer> defaultMap = new EnumMap<>(RPGStatsEnum.class);

        for (RPGStatsEnum stat : RPGStatsEnum.values()) {
            defaultMap.put(stat, stats.getOrDefault(stat, 1));
        }
        stats = Collections.unmodifiableMap(defaultMap);
    }

    public RPGStats() {
        this(Map.of());
    }

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
