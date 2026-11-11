package dev.csilman.rpgstats.dataattachment;

import dev.csilman.rpgstats.RPGStatsMod;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModData {
    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES = DeferredRegister.create(
            NeoForgeRegistries.ATTACHMENT_TYPES, RPGStatsMod.MOD_ID
    );

    public static final Supplier<AttachmentType<RPGStats>> RPG_STATS = ATTACHMENT_TYPES.register(
            "rpg_stats",
            () -> AttachmentType.builder(() -> new RPGStats(RPGStats.DEFAULT))
                    .serialize(RPGStats.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static final Supplier<AttachmentType<RPGLeveling>> RPG_LEVELING = ATTACHMENT_TYPES.register(
            "rpg_leveling",
            () -> AttachmentType.builder(() -> new RPGLeveling(0.0d, 1, 0, 0))
                    .serialize(RPGLeveling.CODEC)
                    .copyOnDeath()
                    .build()
    );

    public static void register(IEventBus eventBus) {
        ATTACHMENT_TYPES.register(eventBus);
    }
}
