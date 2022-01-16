package com.dragonevol.soar.config;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@Mod.EventBusSubscriber(modid = "soar", bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config {
    public static final String CATEGORY_GENERAL = "general";

    public static ForgeConfigSpec SERVER_SPEC;

    public static ForgeConfigSpec.DoubleValue ACC_PROPORTION;
    public static ForgeConfigSpec.DoubleValue DEC_PROPORTION;
    public static ForgeConfigSpec.DoubleValue MAX_SPEED;
    public static ForgeConfigSpec.DoubleValue SPRINT_SPEED;
    public static ForgeConfigSpec.DoubleValue BOOST_MAX_SPEED;
    public static ForgeConfigSpec.DoubleValue BOOST_PROPORTION;
    public static ForgeConfigSpec.DoubleValue EXHAUST_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue SPRINT_EXHAUST_MULTIPLIER;
    public static ForgeConfigSpec.DoubleValue BOOST_EXHAUST_MULTIPLIER;
    public static ForgeConfigSpec.IntValue BOOST_COOLDOWN;

    public static double accProportion;
    public static double decProportion;
    public static double maxSpeed;
    public static double sprintSpeed;
    public static double boostMaxSpeed;
    public static double boostProportion;
    public static double exhaustMultiplier;
    public static double sprintExhaustMultiplier;
    public static double boostExhaustMultiplier;
    public static int boostCooldown;

    static {
        ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();

        SERVER_BUILDER.comment("General config").push(CATEGORY_GENERAL);

        setupGeneralConfig(SERVER_BUILDER);

        SERVER_BUILDER.pop();

        SERVER_SPEC = SERVER_BUILDER.build();
    }

    private static void setupGeneralConfig(ForgeConfigSpec.Builder serverBuilder) {
        ACC_PROPORTION = serverBuilder
                .comment("Proportion of acceleration towards max speed per tick when holding KEY_FORWARD (W)")
                .defineInRange("accProportion", 0.03d, 0d,1d);
        DEC_PROPORTION = serverBuilder
                .comment("Proportion of deceleration towards zero speed per tick when holding KEY_BACKWARD (S)")
                .defineInRange("decProportion", 0.04d, 0d,1d);
        MAX_SPEED = serverBuilder
                .comment("Max speed (blocks per tick) achievable when holding KEY_FORWARD (W)")
                .defineInRange("maxSpeed", 1d, 0d,10d);
        SPRINT_SPEED = serverBuilder
                .comment("Max speed (blocks per tick) achievable when holding KEY_FORWARD (W) and sprinting")
                .defineInRange("sprintSpeed", 1.33d, 1d, 10d);
        BOOST_MAX_SPEED = serverBuilder
                .comment("Max speed (blocks per tick) achievable via boosting")
                .defineInRange("boostMaxSpeed", 1.67d, 0d, 10d);
        BOOST_PROPORTION = serverBuilder
                .comment("Proportion of boosting towards max boost speed")
                .defineInRange("boostProportion", 0.8d, 0d, 1d);
        EXHAUST_MULTIPLIER = serverBuilder
                .comment("Multiplier of the hunger drain rate when accelerating/decelerating")
                .defineInRange("exhaustMultiplier", 1d, 0d, 100d);
        SPRINT_EXHAUST_MULTIPLIER = serverBuilder
                .comment("Multiplier of the hunger drain rate when accelerating/decelerating and sprinting")
                .defineInRange("exhaustMultiplier", 2d, 0d, 100d);
        BOOST_EXHAUST_MULTIPLIER = serverBuilder
                .comment("Multiplier of the hunger drain rate when boosting")
                .defineInRange("boostExhaustMultiplier", 3d, 0d, 100d);
        BOOST_COOLDOWN = serverBuilder
                .comment("Cooldown of Elytra Boost (in ticks)")
                .defineInRange("boostCooldown", 60, 5, 1200);
    }

    @SubscribeEvent
    public static void onLoad(final ModConfig.Loading configEvent) {
        if(configEvent.getConfig().getSpec() == SERVER_SPEC) {
            bakeConfig();
        }
    }

    @SubscribeEvent
    public static void onReload(final ModConfig.Reloading configEvent) {
        if(configEvent.getConfig().getSpec() == SERVER_SPEC) {
            bakeConfig();
        }
    }

    // dragon: don't know if this will work
    private static void bakeConfig() {
        accProportion = ACC_PROPORTION.get();
        decProportion = DEC_PROPORTION.get();
        maxSpeed = MAX_SPEED.get();
        sprintSpeed = SPRINT_SPEED.get();
        boostMaxSpeed = BOOST_MAX_SPEED.get();
        boostProportion = BOOST_PROPORTION.get();
        exhaustMultiplier = EXHAUST_MULTIPLIER.get();
        sprintExhaustMultiplier = SPRINT_EXHAUST_MULTIPLIER.get();
        boostExhaustMultiplier = BOOST_EXHAUST_MULTIPLIER.get();
        boostCooldown = BOOST_COOLDOWN.get();
        /*
        Soar.LOGGER.info("debug info: {} from config", accProportion);
        Soar.LOGGER.info("debug info: {} from config", decProportion);
        Soar.LOGGER.info("debug info: {} from config", maxSpeed);
        Soar.LOGGER.info("debug info: {} from config", sprintMultiplier);
        Soar.LOGGER.info("debug info: {} from config", exhaustMultiplier);
         */
    }
}
