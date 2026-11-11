package dev.csilman.rpgstats.util;

import com.mojang.serialization.Codec;
import net.minecraft.util.StringRepresentable;
import org.jetbrains.annotations.NotNull;

public enum RPGStatsEnum implements StringRepresentable {
    Strength("strength"),
    Constitution("constitution"),
    Dexterity("dexterity"),
    Wisdom("wisdom"),
    Intelligence("intelligence"),
    Charisma("charisma");

    public static final Codec<RPGStatsEnum> CODEC = StringRepresentable.fromEnum(RPGStatsEnum::values);

    public final String serializedName;

    RPGStatsEnum(String serializedName) {
        this.serializedName = serializedName;
    }

    @Override
    public @NotNull String getSerializedName() {
        return serializedName;
    }
}

