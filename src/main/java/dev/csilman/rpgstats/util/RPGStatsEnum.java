package dev.csilman.rpgstats.util;

public enum RPGStatsEnum {
    Strength(0),
    Constitution(1),
    Dexterity(2),
    Wisdom(3),
    Intelligence(4),
    Charisma(5);

    public final int index;

    RPGStatsEnum(int index)
    {
        this.index = index;
    }
}
