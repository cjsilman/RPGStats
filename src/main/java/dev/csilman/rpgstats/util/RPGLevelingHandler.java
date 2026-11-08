package dev.csilman.rpgstats.util;

import dev.csilman.rpgstats.Config;
import dev.csilman.rpgstats.dataattachment.ModData;
import dev.csilman.rpgstats.dataattachment.RPGLeveling;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Player;

public class RPGLevelingHandler {

    public static void addRPGLevelingXP(Player player, double value) {
        // Skip creative
        if (player.isCreative()) {
            //return;
        }

        // Level increase
        RPGLeveling playerLevelData = player.getData(ModData.RPG_LEVELING);
        double playerNewXPValue = playerLevelData.xp() + value;
        int playerLevel = playerLevelData.level();

        player.sendSystemMessage(Component.literal(String.format(
                "%s gained %s XP (%f -> %f). %f/%f. Level: %d",
                player.getName().getString(),
                value,
                playerLevelData.xp(),
                playerNewXPValue,
                playerNewXPValue,
                determineXPForNextLevel(playerLevel),
                playerLevel)));

        if (availableForLevelUp(playerLevelData, playerNewXPValue))
        {
            // Defer to the level up method that will determine attribute and milestone points
            // levelUpPlayer calls setData
            playerLevel = playerLevelData.level() + 1;
            player.setData(ModData.RPG_LEVELING, getLeveledUpData(player, playerLevel));
        } else {
            // Just update the players XP points, maintaining the same for all other values
            player.setData(ModData.RPG_LEVELING, new RPGLeveling(
                    playerNewXPValue,
                    playerLevel,
                    Config.MAX_LEVEL.get(),
                    playerLevelData.attributePoints(),
            playerLevelData.milestonePoints()
            ));
        }
    }

    private static boolean availableForLevelUp(RPGLeveling levelingData, double currentXP) {
        // Determine if the current XP is enough for a player to increase level
        int currentLevel = levelingData.level();
        int currentMaxLevel = levelingData.maxLevel();
        double xpToLevelUp;

        if (currentLevel == currentMaxLevel) {
            return false;
        } else {
            xpToLevelUp = determineXPForNextLevel(currentLevel);
        }

        return currentXP >= xpToLevelUp;
    }

    private static double determineXPForNextLevel(int currentLevel) {

        double xpToLevelUp = Double.MAX_VALUE;

        // Determine what formula to use based on level range
        if (currentLevel >= 1 && currentLevel <= 80) {
            // Formula is y = 1.25x
            xpToLevelUp = 1.25*currentLevel;
        }

        return xpToLevelUp;

    }

    private static RPGLeveling getLeveledUpData(Player player, int levelAchieved) {
        int currentAttributePoints = player.getData(ModData.RPG_LEVELING).attributePoints();
        int currentMilestonePoints = player.getData(ModData.RPG_LEVELING).milestonePoints();

        // Determine Milestone Points
        if (Config.MILESTONE_LEVELS.get().contains(levelAchieved)) {
            currentMilestonePoints += 1;
        }

        // Reset experience, add attribute/milestone point, maintain level
        return new RPGLeveling(0, levelAchieved, Config.MAX_LEVEL.get(), currentAttributePoints+1, currentMilestonePoints);
    }

}
