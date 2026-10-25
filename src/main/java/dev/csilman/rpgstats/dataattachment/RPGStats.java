package dev.csilman.rpgstats.dataattachment;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import dev.csilman.rpgstats.util.RPGStatsEnum;
import net.minecraft.server.level.ServerPlayer;

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

    public int getRPGStat(RPGStatsEnum statType) {
        if (statType == RPGStatsEnum.Strength) {
            return strength;
        } else if (statType == RPGStatsEnum.Constitution) {
            return constitution;
        } else if (statType == RPGStatsEnum.Dexterity) {
            return dexterity;
        } else if (statType == RPGStatsEnum.Wisdom) {
            return wisdom;
        } else if (statType == RPGStatsEnum.Intelligence) {
            return intelligence;
        } else if (statType == RPGStatsEnum.Charisma) {
            return charisma;
        } else {
            return 0; // 0 is an invalid value for an RPG stat, should denote a problem
        }
    }

    public RPGStats setRPGStat(ServerPlayer player, RPGStatsEnum statType, int value) {
        RPGStats oldStats = player.getData(ModData.RPG_STATS);

        return updateRPGStats(oldStats, statType, value);
    }

    private RPGStats updateRPGStats(RPGStats oldStats, RPGStatsEnum statType, int value) {
        int strength = oldStats.strength;
        int constitution = oldStats.constitution;
        int dexterity = oldStats.dexterity;
        int wisdom = oldStats.wisdom;
        int intelligence = oldStats.intelligence;
        int charisma = oldStats.charisma;

        if (statType == RPGStatsEnum.Strength) {
            strength = value;
        } else if (statType == RPGStatsEnum.Constitution) {
            constitution = value;
        } else if (statType == RPGStatsEnum.Dexterity) {
            dexterity = value;
        } else if (statType == RPGStatsEnum.Wisdom) {
            wisdom = value;
        } else if (statType == RPGStatsEnum.Intelligence) {
            intelligence = value;
        } else if (statType == RPGStatsEnum.Charisma) {
            charisma = value;
        }

        return new RPGStats(strength, constitution, dexterity, wisdom, intelligence, charisma);
    }
}
