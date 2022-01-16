package com.dragonevol.soar.events;

import com.dragonevol.soar.Soar;
import com.dragonevol.soar.config.Config;
import com.dragonevol.soar.network.Network;
import com.dragonevol.soar.network.packet.AddExhaustionPacket;
import com.dragonevol.soar.network.packet.ToggleElytraPacket;
import com.dragonevol.soar.key.KeyRegistry;
import com.dragonevol.soar.util.VelocityUtil;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.common.Mod;

// dragon: Dist.CLIENT only ensures these are subscribed on PHYSICAL CLIENT
@Mod.EventBusSubscriber(modid = "soar", bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class EventsClient {
    // dragon: register keybinding for toggle elytra flying.
    @SubscribeEvent
    public static void onKeyInput(InputEvent.KeyInputEvent event) {
        // dragon: toggle elytra
        if(KeyRegistry.TOGGLE_ELYTRA.isDown() && !KeyRegistry.IS_HOLDING_TOGGLE_ELYTRA) {
            Network.CHANNEL.sendToServer(new ToggleElytraPacket());
            // dragon: update the state of TOGGLE_ELYTRA key, so only act when key is pressed
            KeyRegistry.IS_HOLDING_TOGGLE_ELYTRA = true;
            // Soar.LOGGER.info("debug info: Pressed TOGGLE_ELYTRA");
        }

        // dragon: elytra boost
        if(KeyRegistry.ELYTRA_BOOST.isDown() && KeyRegistry.ELYTRA_BOOST_COOLDOWN == 0) {
            // Soar.LOGGER.info("boost");
            KeyRegistry.ELYTRA_BOOST_COOLDOWN = Config.boostCooldown;
        }
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        // dragon: logical side check
        if(event.side != LogicalSide.CLIENT) return;

        // dragon: update the state of TOGGLE_ELYTRA key
        if(!KeyRegistry.TOGGLE_ELYTRA.isDown() && KeyRegistry.IS_HOLDING_TOGGLE_ELYTRA) {
            KeyRegistry.IS_HOLDING_TOGGLE_ELYTRA = false;
            // Soar.LOGGER.info("debug info: Released TOGGLE_ELYTRA");
        }

        if(event.phase == TickEvent.Phase.START) {
            // dragon: casting PlayerEntity to ClientPlayerEntity... this should be fine? since we are on CLIENT?
            ClientPlayerEntity player = (ClientPlayerEntity) event.player;
            if(player == null) return;

            // dragon: handle boost effect
            if(KeyRegistry.ELYTRA_BOOST_COOLDOWN > 0) {
                if(player.isFallFlying()) {
                    Vector3d velocityChangeBoost = VelocityUtil.calcVelocityChangeBoost(player);
                    if (velocityChangeBoost.length() > 0) {
                        player.setDeltaMovement(player.getDeltaMovement().add(velocityChangeBoost));
                        Network.CHANNEL.sendToServer(new AddExhaustionPacket(
                                velocityChangeBoost.length() * Config.boostExhaustMultiplier));
                        // Soar.LOGGER.info("boosting {}", velocityChangeBoost.length());
                    }
                    KeyRegistry.ELYTRA_BOOST_COOLDOWN--;
                } else {
                    KeyRegistry.ELYTRA_BOOST_COOLDOWN = 0;
                }
            }

            // dragon: handle acceleration/deceleration effects
            if(player.isFallFlying()) {
                Vector3d velocityChangeAcceleration = VelocityUtil.calcVelocityChangeAcceleration(player);
                if(velocityChangeAcceleration.length() > 0) {
                    // dragon: accelerate/decelerate, done in client side
                    player.setDeltaMovement(player.getDeltaMovement().add(velocityChangeAcceleration));
                    // dragon: drain hunger
                    Network.CHANNEL.sendToServer(new AddExhaustionPacket(
                            velocityChangeAcceleration.length() *
                            (player.isSprinting() ? Config.sprintExhaustMultiplier : Config.exhaustMultiplier)));
                }
            }
        }
    }
}
