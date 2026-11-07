package dev.csilman.rpgstats.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.attributes.ModAttributes;
import dev.csilman.rpgstats.dataattachment.ModData;
import dev.csilman.rpgstats.dataattachment.RPGLeveling;
import dev.csilman.rpgstats.dataattachment.RPGStats;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DebugPrintRPGStatCommandServer {

    public DebugPrintRPGStatCommandServer(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal(RPGStatsMod.MOD_ID)
                        .requires(commandSourceStack -> commandSourceStack.hasPermission(Commands.LEVEL_GAMEMASTERS))
                        .then(Commands.literal("debug")
                                .then(Commands.literal("stats")
                                        .then(Commands.literal("server")
                                                .executes(this::execute)))));
    }

    private int execute(CommandContext<CommandSourceStack> commandSourceStackCommandContext) throws CommandSyntaxException {
        ServerPlayer player = commandSourceStackCommandContext.getSource().getPlayerOrException();
        RPGStats stats = player.getData(ModData.RPG_STATS);
        RPGLeveling leveling = player.getData(ModData.RPG_LEVELING);
        double strengthAttribute = player.getAttributeValue(ModAttributes.STRENGTH);

        commandSourceStackCommandContext.getSource().sendSuccess(
                () -> Component.literal("[SERVER] RPG Stats = " + stats + " | Leveling = " + leveling  + " | STR attribute = " + strengthAttribute),
                true
        );

        return Command.SINGLE_SUCCESS;
    }
}
