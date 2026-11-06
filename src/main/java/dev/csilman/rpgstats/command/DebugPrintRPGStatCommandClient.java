package dev.csilman.rpgstats.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.attributes.ModAttributes;
import dev.csilman.rpgstats.dataattachment.ModData;
import dev.csilman.rpgstats.dataattachment.RPGStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class DebugPrintRPGStatCommandClient {

    public DebugPrintRPGStatCommandClient(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal(RPGStatsMod.MOD_ID)
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.literal("debug")
                                .then(Commands.literal("stats")
                                        .then(Commands.literal("client")
                                                .executes(this::execute)))));
    }

    private int execute(CommandContext<CommandSourceStack> commandSourceStackCommandContext) {
        LocalPlayer player = Minecraft.getInstance().player;

        if (player == null) return 0;

        RPGStats stats = player.getData(ModData.RPG_STATS);
        double strengthAttribute = player.getAttributeValue(ModAttributes.STRENGTH);

        commandSourceStackCommandContext.getSource().sendSuccess(
                () -> Component.literal("[CLIENT] RPG Stats = " + stats + " | STR attribute = " + strengthAttribute),
                true
        );

        return Command.SINGLE_SUCCESS;

    }

}
