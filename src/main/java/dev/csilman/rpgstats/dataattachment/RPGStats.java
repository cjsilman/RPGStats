package dev.csilman.rpgstats.dataattachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.csilman.rpgstats.util.RPGStatsEnum;
import net.minecraft.server.level.ServerPlayer;

import java.util.stream.IntStream;
import java.util.Arrays;

public record RPGStats(int[] stats) {

    public static final Codec<RPGStats> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    Codec.INT_STREAM
                         .xmap(IntStream::toArray, Arrays::stream)
                         .optionalFieldOf("stats", new int[]{1,1,1,1,1,1})
                         .forGetter(RPGStats::stats)
            ).apply(instance, RPGStats::new)
    );

    public RPGStats(int strength, int constitution, int dexterity, int wisdom, int intelligence, int charisma)
    {
        this(new int[]{strength, constitution, dexterity, wisdom, intelligence, charisma});
    }

    public int getRPGStat(RPGStatsEnum statType) {
        return stats[statType.index];
    }

    public void setRPGStat(ServerPlayer player, RPGStatsEnum statType, int value) {
        RPGStats oldStats = player.getData(ModData.RPG_STATS);
        oldStats.stats[statType.index] = value;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof RPGStats(int[] otherStats))) return false;
        return Arrays.equals(this.stats, otherStats);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(stats);
    }
}
