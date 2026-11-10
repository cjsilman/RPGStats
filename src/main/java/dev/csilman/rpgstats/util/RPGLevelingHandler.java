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

        RPGLeveling playerLevelData = player.getData(ModData.RPG_LEVELING);
        double playerNewXP = playerLevelData.xp() + value;

        // Debugging purposes until GUI made
        player.sendSystemMessage(Component.literal(String.format(
                "%s gained %s XP (%f -> %f). %f/%f. Level: %d",
                player.getName().getString(),
                value,
                playerLevelData.xp(),
                playerNewXP,
                playerNewXP,
                determineXPForNextLevel(playerLevelData.level()),
                playerLevelData.level())));

        // Determine level increase
        if (availableForLevelUp(playerLevelData.level(), Config.MAX_LEVEL.get(), playerNewXP))
        {
            levelUpPlayer(player, playerLevelData.level()+1);
        } else {
            // Just update the players XP points, maintaining the same for all other values
            player.setData(ModData.RPG_LEVELING, new RPGLeveling(
                    playerNewXP,
                    playerLevelData.level(),
                    playerLevelData.attributePoints(),
                    playerLevelData.milestonePoints()
            ));
        }
    }

    private static boolean availableForLevelUp(int currentLevel, int currentMaxLevel, double currentXP) {
        // Determine if the current XP is enough for a player to increase level
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

    private static void levelUpPlayer(Player player, int targetLevel) {
        // Doesn't account for levels in between current to target level
        int currentAttributePoints = player.getData(ModData.RPG_LEVELING).attributePoints();
        int currentMilestonePoints = player.getData(ModData.RPG_LEVELING).milestonePoints();

        // Determine Milestone Points
        if (Config.MILESTONE_LEVELS.get().contains(targetLevel)) {
            currentMilestonePoints += 1;
        }

        // Reset experience, add attribute/milestone points, maintain level
        RPGLeveling newData = new RPGLeveling(0, targetLevel, currentAttributePoints+1, currentMilestonePoints);
        player.setData(ModData.RPG_LEVELING, newData);
    }

}
