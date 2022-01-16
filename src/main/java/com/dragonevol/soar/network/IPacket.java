package com.dragonevol.soar.network;

import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

import java.util.function.Supplier;

// dragon: coming from code of ProjectE
public interface IPacket {
    void handle(NetworkEvent.Context context);

    void encode(PacketBuffer buffer);

    static <PACKET extends IPacket> void handle(final PACKET message, Supplier<NetworkEvent.Context> ctx) {
        NetworkEvent.Context context = ctx.get();
        context.enqueueWork(() -> message.handle(context));
        context.setPacketHandled(true);
    }
}
