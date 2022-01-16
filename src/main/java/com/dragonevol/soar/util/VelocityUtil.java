package com.dragonevol.soar.util;

import com.dragonevol.soar.config.Config;
import com.dragonevol.soar.key.KeyRegistry;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

public class VelocityUtil {
    public static Vector3d calcVelocityChangeAcceleration(ClientPlayerEntity player) {
        Vector3d look = player.getLookAngle();
        Vector3d velocity = player.getDeltaMovement();
        float forward = player.input.forwardImpulse;

        double parallelSpeed = look.dot(velocity);
        double parallelSpeedAdd = 0;
        double maxSpeed = player.isSprinting() ? Config.sprintSpeed : Config.maxSpeed;
        if(forward > 0) {
            parallelSpeedAdd = Math.max(0, Config.accProportion * forward * (maxSpeed - parallelSpeed));
        } else if(forward < 0) {
            parallelSpeedAdd = Config.decProportion * forward * parallelSpeed;
        }
        return look.scale(parallelSpeedAdd);
    }


    // dragon: distribute the boost effect into 3 ticks
    public static final int timeInterval = 3;
    public static double[] distribute = {0.25, 0.5, 0.25};
    public static double[] distributeDone = {0, 0.25, 0.75};
    public static Vector3d calcVelocityChangeBoost(ClientPlayerEntity player) {
        int state = Config.boostCooldown - KeyRegistry.ELYTRA_BOOST_COOLDOWN;
        if(state >= timeInterval) {
            return new Vector3d(0, 0, 0);
        }

        Vector3d look = player.getLookAngle();
        Vector3d velocity = player.getDeltaMovement();
        double multiplier = Config.boostProportion * distribute[state] /
                (1.0d - Config.boostProportion * distributeDone[state]);
        double parallelSpeed = look.dot(velocity);
        double parallelSpeedAdd;
        parallelSpeedAdd = Math.max(
                0, multiplier * (Config.boostMaxSpeed - parallelSpeed));
        return look.scale(parallelSpeedAdd);
    }
}
