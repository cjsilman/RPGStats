package dev.csilman.rpgstats.event;

import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.attributes.ModAttributes;
import dev.csilman.rpgstats.util.RPGStatsHandler;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.player.Player;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.entity.EntityAttributeModificationEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;

import java.util.ArrayList;

@EventBusSubscriber(modid = RPGStatsMod.MOD_ID)
public class ModEvents {

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
    public static void setPlayerStatsOnSpawn(PlayerEvent.PlayerLoggedInEvent event) {
        // Only Server Side
        Player player = event.getEntity();
        RPGStatsHandler.applyStats(player);
    }

}
