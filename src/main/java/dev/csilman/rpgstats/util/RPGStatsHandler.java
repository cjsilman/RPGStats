package dev.csilman.rpgstats.util;

import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.attributes.ModAttributes;
import dev.csilman.rpgstats.dataattachment.ModData;
import dev.csilman.rpgstats.dataattachment.RPGStats;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.ai.attributes.AttributeMap;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;

import java.util.Objects;

public class RPGStatsHandler {

    private static final ResourceLocation STR_MOD_ID = ResourceLocation.fromNamespaceAndPath(RPGStatsMod.MOD_ID, "str_mod_id");
    private static final ResourceLocation CON_MOD_ID = ResourceLocation.fromNamespaceAndPath(RPGStatsMod.MOD_ID, "con_mod_id");
    private static final ResourceLocation DEX_MOD_ID = ResourceLocation.fromNamespaceAndPath(RPGStatsMod.MOD_ID, "dex_mod_id");
    private static final ResourceLocation WIS_MOD_ID = ResourceLocation.fromNamespaceAndPath(RPGStatsMod.MOD_ID, "wis_mod_id");
    private static final ResourceLocation INT_MOD_ID = ResourceLocation.fromNamespaceAndPath(RPGStatsMod.MOD_ID, "int_mod_id");
    private static final ResourceLocation CHA_MOD_ID = ResourceLocation.fromNamespaceAndPath(RPGStatsMod.MOD_ID, "cha_mod_id");

    public static void applyStats(Player player) {
        // Needs to be called whenever a player's RPGStats gets updated.
        // This is what applies all the changes to the attributes.
        RPGStats stats = player.getData(ModData.RPG_STATS);

        // Intellij suggested requiring non-null, but I am pretty sure getData will just set the default values lol
        Objects.requireNonNull(
                player.getAttribute(ModAttributes.STRENGTH),
                String.format("[%s] Attempted to set player Strength attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.getRPGStat(RPGStatsEnum.Strength));

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.CONSTITUTION),
                String.format("[%s] Attempted to set player Constitution attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.getRPGStat(RPGStatsEnum.Constitution));

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.DEXTERITY),
                String.format("[%s] Attempted to set player Dexterity attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.getRPGStat(RPGStatsEnum.Dexterity));

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.WISDOM),
                String.format("[%s] Attempted to set player Wisdom attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.getRPGStat(RPGStatsEnum.Wisdom));

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.INTELLIGENCE),
                String.format("[%s] Attempted to set player Intelligence attribute to null", RPGStatsMod.MOD_ID)
        ).setBaseValue(stats.getRPGStat(RPGStatsEnum.Intelligence));

        Objects.requireNonNull(
                player.getAttribute(ModAttributes.CHARISMA),
                String.format("[%s] Attempted to set player Strength Charisma to null", RPGStatsMod.MOD_ID
                )).setBaseValue(stats.getRPGStat(RPGStatsEnum.Charisma));

        refreshStatEffects(player);
    }

    private static void refreshStatEffects(Player player) {
        // Update attributes based on RPGStat attributes
        // The attributes are such that equipment that increases your RPGState attribute will affect modifiers
        AttributeMap attributes = player.getAttributes();

        // Strength
        double strength = attributes.getValue(ModAttributes.STRENGTH);
        double strengthMod = Math.floor((strength)/2.0d)+((0.25d*(strength-1))); // 1 attack damage every 2 levels + 0.25 per level. Adjusted to be zero at level 1
        AttributeModifier modifier = new AttributeModifier(
            STR_MOD_ID, strengthMod, AttributeModifier.Operation.ADD_VALUE
        );

        Objects.requireNonNull(attributes.getInstance(Attributes.ATTACK_DAMAGE)).addOrUpdateTransientModifier(modifier);

        // Constitution



    }
}
