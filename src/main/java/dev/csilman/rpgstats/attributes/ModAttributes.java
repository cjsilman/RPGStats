package dev.csilman.rpgstats.attributes;

import dev.csilman.rpgstats.RPGStatsMod;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModAttributes {

    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(
            BuiltInRegistries.ATTRIBUTE, RPGStatsMod.MOD_ID
    );

    public static final Holder<Attribute> STRENGTH = ATTRIBUTES.register("strength",
            () -> new RangedAttribute(
                    "attributes.rpgstats.strength",
                    1,
                    1,
                    20
            ));

    public static final Holder<Attribute> CONSTITUTION = ATTRIBUTES.register("constitution",
            () -> new RangedAttribute(
                    "attributes.rpgstats.constitution",
                    1,
                    1,
                    20
            ));

    public static final Holder<Attribute> DEXTERITY = ATTRIBUTES.register("dexterity",
            () -> new RangedAttribute(
                    "attributes.rpgstats.dexterity",
                    1,
                    1,
                    20
            ));

    public static final Holder<Attribute> WISDOM = ATTRIBUTES.register("wisdom",
            () -> new RangedAttribute(
                    "attributes.rpgstats.wisdom",
                    1,
                    1,
                    20
            ));

    public static final Holder<Attribute> INTELLIGENCE = ATTRIBUTES.register("intelligence",
            () -> new RangedAttribute(
                    "attributes.rpgstats.intelligence",
                    1,
                    1,
                    20
            ));

    public static final Holder<Attribute> CHARISMA = ATTRIBUTES.register("charisma",
            () -> new RangedAttribute(
                    "attributes.rpgstats.charisma",
                    1,
                    1,
                    20
            ));



    public static void register(IEventBus eventBus) {
        ATTRIBUTES.register(eventBus);
    }
}
