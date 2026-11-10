package dev.csilman.rpgstats.event;

import dev.csilman.rpgstats.Config;
import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.attributes.ModAttributes;
import dev.csilman.rpgstats.command.DebugPrintRPGStatCommandServer;
import dev.csilman.rpgstats.command.SetRPGStatCommand;
import dev.csilman.rpgstats.util.RPGLevelingHandler;
import dev.csilman.rpgstats.util.RPGStatsHandler;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.living.LivingDeathEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.entity.player.PlayerXpEvent;
import net.neoforged.neoforge.event.level.BlockEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

import java.util.ArrayList;

@EventBusSubscriber(modid = RPGStatsMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void registerCommands(RegisterCommandsEvent event) {
        new SetRPGStatCommand(event.getDispatcher());
        new DebugPrintRPGStatCommandServer(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

    @SubscribeEvent
    public static void registerAttributes(EntityAttributeModificationEvent event) {

        ArrayList<Holder<Attribute>> rpgAttributes = new ArrayList<>();
        rpgAttributes.add(ModAttributes.STRENGTH);
        rpgAttributes.add(ModAttributes.CONSTITUTION);
        rpgAttributes.add(ModAttributes.DEXTERITY);
        rpgAttributes.add(ModAttributes.WISDOM);
        rpgAttributes.add(ModAttributes.INTELLIGENCE);
        rpgAttributes.add(ModAttributes.CHARISMA);

        for (Holder<Attribute> attribute : rpgAttributes)
        {
            if (!event.has(EntityType.PLAYER, attribute))
            {
                event.add(
                        EntityType.PLAYER,
                        attribute
                );
            }
        }

    }

    @SubscribeEvent
    public static void earnXPOnEntityKill(LivingDeathEvent event) {
        // Is Server Side and Client Side
        LivingEntity killedEntity = event.getEntity();

        if (!killedEntity.level().isClientSide()) {
            // Only process event on server side
            // Syncing to client will be covered elsewhere
            String entityID = BuiltInRegistries.ENTITY_TYPE.getKey(killedEntity.getType()).toString();

            if (Config.ENTITY_KILL_BLOCKLIST.get().contains(entityID)) {
                return;
            }

            if (event.getSource().getEntity() instanceof Player player) {
                // TODO: Add AABB allowing other nearby players to gain experience. Only for bosses
                if (Config.BOSS_KILL_WHITELIST.get().contains(entityID)) {
                    RPGLevelingHandler.addRPGLevelingXP(player, Config.BASE_XP_COST.get() * Config.BOSS_ENTITY_KILL_MOD.get());
                } else {
                    RPGLevelingHandler.addRPGLevelingXP(player, Config.BASE_XP_COST.get() * Config.ENTITY_KILL_MOD.get());
                }
            }
        }
    }

    @SubscribeEvent
    public static void earnXPOnBlockBreak(BlockEvent.BreakEvent event) {
        RPGLevelingHandler.addRPGLevelingXP(event.getPlayer(), Config.BASE_XP_COST.get()*Config.BLOCK_BREAK_XP_MOD.get());
    }

    @SubscribeEvent
    public static void earnXPOnBlockPlace(BlockEvent.EntityPlaceEvent event) {
        if (event.getEntity() instanceof Player) {
            RPGLevelingHandler.addRPGLevelingXP((Player) event.getEntity(), Config.BASE_XP_COST.get()*Config.BLOCK_PLACE_XP_MOD.get());
        }
    }

    @SubscribeEvent
    public static void earnXPOnMinecraftXPGain(PlayerXpEvent.PickupXp event) {
        RPGLevelingHandler.addRPGLevelingXP(event.getEntity(), Config.BASE_XP_COST.get()*Config.REGULAR_XP_MOD.get());
    }

    @SubscribeEvent
    public static void setPlayerStatsOnSpawn(PlayerEvent.PlayerLoggedInEvent event) {
        // Only Server Side
        Player player = event.getEntity();
        RPGStatsHandler.applyStats(player);
    }

    @SubscribeEvent
    public static void setPlayerStatsOnDimensionChange(final PlayerEvent.PlayerChangedDimensionEvent event) {
        // Only Server Side
        Player player = event.getEntity();
        RPGStatsHandler.applyStats(player);
    }

    @SubscribeEvent
    public static void setPlayerStatsOnClone(final PlayerEvent.Clone event) {
        // Only Server Side
        Player player = event.getEntity();
        RPGStatsHandler.applyStats(player);
    }

    @SubscribeEvent
    public static void setPlayerStatsOnRespawn(final PlayerEvent.PlayerRespawnEvent event) {
        // Only Server Side
        Player player = event.getEntity();
        RPGStatsHandler.applyStats(player);
    }

}
