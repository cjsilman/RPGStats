package dev.csilman.rpgstats.util;

import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.attributes.ModAttributes;
import dev.csilman.rpgstats.dataattachment.ModData;
import dev.csilman.rpgstats.dataattachment.RPGStats;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class RPGStatsHandler {

    public static void applyStats(Player player) {
        // Needs to be called whenever a player's RPGStats gets updated.
        // This is what applies all the changes to the attributes.

        RPGStats stats = player.getData(ModData.RPG_STATS);

        // Intellij suggested requiring non-null, but I am pretty sure getData will just set the default values lol
        Objects.requireNonNull(
                player.getAttribute(ModAttributes.STRENGTH),
                String.format("[%s] Attempted to set player Strength attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.strength());

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.CONSTITUTION),
                String.format("[%s] Attempted to set player Constitution attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.constitution());

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.DEXTERITY),
                String.format("[%s] Attempted to set player Dexterity attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.dexterity());

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.WISDOM),
                String.format("[%s] Attempted to set player Wisdom attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.wisdom());

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.INTELLIGENCE),
                String.format("[%s] Attempted to set player Intelligence attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.intelligence());

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.CHARISMA),
                String.format("[%s] Attempted to set player Strength Charisma to null", RPGStatsMod.MOD_ID
                )).setBaseValue(stats.charisma());

        refreshStatEffects(player);
    }

    private static void refreshStatEffects(Player player) {
        // Update attributes based on RPGStat attributes
    }
}
