package dev.csilman.rpgstats.event;

import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.command.DebugPrintRPGStatCommandClient;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RegisterClientCommandsEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = RPGStatsMod.MOD_ID, value = Dist.CLIENT)
public class ModClientEvents {

    @SubscribeEvent
    public static void registerClientCommands(RegisterClientCommandsEvent event) {
        new DebugPrintRPGStatCommandClient(event.getDispatcher());

        ConfigCommand.register(event.getDispatcher());
    }

}
