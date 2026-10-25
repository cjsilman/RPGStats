package dev.csilman.rpgstats.command;

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import dev.csilman.rpgstats.RPGStatsMod;
import dev.csilman.rpgstats.dataattachment.ModData;
import dev.csilman.rpgstats.dataattachment.RPGStats;
import dev.csilman.rpgstats.util.RPGStatsEnum;
import dev.csilman.rpgstats.util.RPGStatsHandler;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.server.command.EnumArgument;

public class SetRPGStatCommand {

    public SetRPGStatCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal(RPGStatsMod.MOD_ID)
                .then(Commands.argument("target", EntityArgument.player())
                        .then(Commands.literal("set")
                                .then(Commands.literal("base")
                                        .then(Commands.argument("stat", EnumArgument.enumArgument(RPGStatsEnum.class))
                                                .then(Commands.argument("value", IntegerArgumentType.integer(1))
                                                        .executes(this::execute)))))));
    }

    private int execute(CommandContext<CommandSourceStack> context) throws CommandSyntaxException {
        ServerPlayer player = EntityArgument.getPlayer(context, "target");
        RPGStatsEnum statType = context.getArgument("stat", RPGStatsEnum.class);
        int value = IntegerArgumentType.getInteger(context, "value");

        RPGStats currentStats = player.getData(ModData.RPG_STATS);
        int currentValue = currentStats.getRPGStat(statType);

        player.setData(ModData.RPG_STATS, currentStats.setRPGStat(player, statType, value));

        player.sendSystemMessage(Component.literal(String.format("Set %s %s to %d (was %d)", player.getName().getString(), statType, value, currentValue)));
        RPGStatsHandler.applyStats(player);

        return Command.SINGLE_SUCCESS;

    }


}
