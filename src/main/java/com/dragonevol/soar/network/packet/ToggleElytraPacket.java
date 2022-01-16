package com.dragonevol.soar.network.packet;

import com.dragonevol.soar.Soar;
import com.dragonevol.soar.network.IPacket;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraftforge.fml.network.NetworkEvent;
import top.theillusivec4.caelus.api.CaelusApi;

// dragon: sent from client to server
public class ToggleElytraPacket implements IPacket {
    public ToggleElytraPacket() {

    }

    @Override
    public void handle(NetworkEvent.Context context) {
        ServerPlayerEntity player = context.getSender();
        if(player == null) {
            return;
        }
        if(CaelusApi.canElytraFly(player)) {
            if(player.isFallFlying()) {
                player.stopFallFlying();
                // Soar.LOGGER.info("debug info: stop elytra flying");
            } else if(!player.isOnGround()) {
                player.startFallFlying();
                // Soar.LOGGER.info("debug info: start elytra flying");
            }
        }
    }

    @Override
    public void encode(PacketBuffer buffer) {

    }

    public static ToggleElytraPacket decode(PacketBuffer buffer) {
        return new ToggleElytraPacket();
    }
}
