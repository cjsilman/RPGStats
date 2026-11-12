package dev.csilman.rpgstats.networking;

import dev.csilman.rpgstats.dataattachment.RPGStats;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.attachment.AttachmentSyncHandler;
import net.neoforged.neoforge.attachment.IAttachmentHolder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class RPGStatsSyncHandler implements AttachmentSyncHandler<RPGStats> {
    @Override
    public void write(@NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @NotNull RPGStats rpgStats, boolean initialSync) {
        // On the server
        if (initialSync) {
            RPGStats.STREAM_CODEC.encode(registryFriendlyByteBuf, rpgStats.stats());
        } else {
            // Write update data
            // Don't need to update individual data, can just send full new one
            RPGStats.STREAM_CODEC.encode(registryFriendlyByteBuf, rpgStats.stats());
        }
    }

    @Override
    public @Nullable RPGStats read(@NotNull IAttachmentHolder iAttachmentHolder, @NotNull RegistryFriendlyByteBuf registryFriendlyByteBuf, @Nullable RPGStats previousRPGStats) {
        // On the Client
        if (previousRPGStats == null) {
            // No data currently exists in the client
            return new RPGStats(RPGStats.STREAM_CODEC.decode(registryFriendlyByteBuf));
        } else {
            // Read the updated data from the buffer and merge with previous value
            // Should be able to just update all data?
            return new RPGStats(RPGStats.STREAM_CODEC.decode(registryFriendlyByteBuf));
        }
    }

    @Override
    public boolean sendToPlayer(@NotNull IAttachmentHolder holder, @NotNull ServerPlayer to) {
        // Only send the attachment if they are the attachment holder
        return holder == to;
    }
}
