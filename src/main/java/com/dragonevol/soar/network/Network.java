package com.dragonevol.soar.network;

import com.dragonevol.soar.Soar;
import com.dragonevol.soar.network.packet.AddExhaustionPacket;
import com.dragonevol.soar.network.packet.ToggleElytraPacket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.network.NetworkRegistry;
import net.minecraftforge.fml.network.simple.SimpleChannel;

public class Network {
    public static SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
            new ResourceLocation(Soar.MODID, "main_channel"), () -> "1.0", s -> true, s -> true);
    public static int ID = 0;

    private static int nextID() {
        return ID++;
    }

    public static void registerMessages() {
        CHANNEL.registerMessage(nextID(), ToggleElytraPacket.class, ToggleElytraPacket::encode,
                ToggleElytraPacket::decode, IPacket::handle);
        CHANNEL.registerMessage(nextID(), AddExhaustionPacket.class, AddExhaustionPacket::encode,
                AddExhaustionPacket::decode, IPacket::handle);
    }
}
