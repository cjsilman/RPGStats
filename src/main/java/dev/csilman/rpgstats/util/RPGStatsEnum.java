package dev.csilman.rpgstats.util;

import com.mojang.serialization.Codec;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.util.ByIdMap;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

import java.util.function.IntFunction;

public enum RPGStatsEnum implements StringRepresentable {
    Strength("strength"),
    Constitution("constitution"),
    Dexterity("dexterity"),
    Wisdom("wisdom"),
    Intelligence("intelligence"),
    Charisma("charisma");

    public static final Codec<RPGStatsEnum> CODEC = StringRepresentable.fromEnum(RPGStatsEnum::values);

    public final String serializedName;

    // Neoforge Id Mapper from https://docs.neoforged.net/docs/networking/streamcodecs/
    public static final IntFunction<RPGStatsEnum> BY_ID =
            ByIdMap.continuous(
                    RPGStatsEnum::ordinal, // Represents ordinal of enumeration constant (i.e. position in enum declaration)
                    RPGStatsEnum.values(),
                    ByIdMap.OutOfBoundsStrategy.ZERO
            );

    public static final StreamCodec<ByteBuf, RPGStatsEnum> STREAM_CODEC =
            ByteBufCodecs.idMapper(RPGStatsEnum.BY_ID, RPGStatsEnum::ordinal);

    RPGStatsEnum(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }
}

