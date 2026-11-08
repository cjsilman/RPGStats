package dev.csilman.rpgstats;

import java.util.List;

import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.common.ModConfigSpec;

// An example config class. This is not required, but it's a good idea to have one to keep your config organized.
// Demonstrates how to use Neo's config APIs
public class Config {
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    public static final ModConfigSpec.BooleanValue LOG_DIRT_BLOCK = BUILDER
            .comment("Whether to log the dirt block on common setup")
            .define("logDirtBlock", true);

    public static final ModConfigSpec.IntValue MAGIC_NUMBER = BUILDER
            .comment("A magic number")
            .defineInRange("magicNumber", 42, 0, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<String> MAGIC_NUMBER_INTRODUCTION = BUILDER
            .comment("What you want the introduction message to be for the magic number")
            .define("magicNumberIntroduction", "The magic number is... ");

    // a list of strings that are treated as resource locations for items
    public static final ModConfigSpec.ConfigValue<List<? extends String>> ITEM_STRINGS = BUILDER
            .comment("A list of items to log on common setup.")
            .defineListAllowEmpty("items", List.of("minecraft:iron_ingot"), () -> "", Config::validateItemName);

    public static final ModConfigSpec.IntValue MAX_LEVEL = BUILDER
            .comment("Defines the maximum achievable player level")
            .defineInRange("maxLevel", 60, 1, Integer.MAX_VALUE);

    public static final ModConfigSpec.ConfigValue<List<? extends Integer>> MILESTONE_LEVELS = BUILDER
            .comment("A list of each level a milestone point will be obtained. (e.g. [3, 5, 6])")
            .defineListAllowEmpty("milestone_levels", List.of(3, 5, 6), Config::validateMilestoneAttribute);

    public static final ModConfigSpec.DoubleValue BASE_XP_COST = BUILDER
            .comment("-----------------------------------------------------------\n" +
                    " EXPERIENCE AND MODIFIERS\n" +
                    "-----------------------------------------------------------\n" +
                    "Determines the base cost for RPGLevel experience, all scenario modifiers rely on this value as their multiple.\n" +
                    "e.g. if you have 0.25 as your blockBreakXPModifier, you will gain baseXPValue*blockBreakXPModifier every time you break a block")
            .defineInRange("baseXPValue", 1, 0, Double.MAX_VALUE);

    public static final ModConfigSpec.DoubleValue BLOCK_BREAK_XP_MOD = BUILDER
            .comment("Determines how much experience is gained on a block break.\n")
            .defineInRange("blockBreakXPModifier", 0.01, 0, 100);

    public static final ModConfigSpec.DoubleValue BLOCK_PLACE_XP_MOD = BUILDER
            .comment("Determines how much experience is gained on a block place.\n")
            .defineInRange("blockPlaceXPModifier", 0.01, 0, 100);

    public static final ModConfigSpec.DoubleValue REGULAR_XP_MOD = BUILDER
            .comment("Determines how much RPGLevel experience is gained on Minecraft XP gain.\n" +
                    "This works different than the typical base XP gain in the examples above. " +
                    "RPGLevel experience is gained based on Minecraft xp value * modifier")
            .defineInRange("blockBreakXPModifier", 0.01, 0, 100);

    public static final ModConfigSpec.DoubleValue ENTITY_KILL_MOD = BUILDER
            .comment("Determines how much experience is gained on entity kill")
            .defineInRange("entityKillModifier", 1.0d, 0.0d, 100.0d);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> ENTITY_KILL_BLOCKLIST = BUILDER
            .comment("List of entities that should NOT give experience. Takes precedence over boss whitelist")
            .defineListAllowEmpty("entityKillBlocklist", List.of("minecraft:horse"), () -> "", Config::validateEntityName);

    public static final ModConfigSpec.DoubleValue BOSS_ENTITY_KILL_MOD = BUILDER
            .comment("Determines how much experience is gained on boss entity kill")
            .defineInRange("bossEntityKillModifier", 0.0d, 5.0d, 100.0d);

    public static final ModConfigSpec.ConfigValue<List<? extends String>> BOSS_KILL_WHITELIST = BUILDER
            .comment("List of boss entities affected by boss modifier")
            .defineListAllowEmpty("bossKillWhitelist", List.of("minecraft:ender_dragon", "minecraft:wither"), () -> "", Config::validateEntityName);

    static final ModConfigSpec SPEC = BUILDER.build();

    private static boolean validateEntityName(final Object obj) {
        return obj instanceof String entityName && BuiltInRegistries.ENTITY_TYPE.containsKey(ResourceLocation.parse(entityName));
    }

    private static boolean validateItemName(final Object obj) {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    private static boolean validateMilestoneAttribute(final Object obj) {
        return obj instanceof Integer;
    }
}
