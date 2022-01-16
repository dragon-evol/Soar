package com.dragonevol.soar.network.packet;

import com.dragonevol.soar.Soar;
import com.dragonevol.soar.network.IPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;

// dragon: sent from client to server
public class AddExhaustionPacket implements IPacket {
    private final double exhaust;

    public AddExhaustionPacket(double exhaustIn) {
        this.exhaust = exhaustIn;
    }

    @Override
    public void handle(NetworkEvent.Context context) {
        ServerPlayerEntity player = context.getSender();
        if(player == null) {
            return;
        }
        player.causeFoodExhaustion((float) (exhaust));
        // Soar.LOGGER.info("debug info: exhaust {}", (float) (exhaust));
    }

    @Override
    public void encode(PacketBuffer buffer) {
        buffer.writeDouble(exhaust);
    }

    public static AddExhaustionPacket decode(PacketBuffer buffer) {
        return new AddExhaustionPacket(buffer.readDouble());
    }
}
