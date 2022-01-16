package com.dragonevol.soar;

import com.dragonevol.soar.config.Config;
import com.dragonevol.soar.network.Network;
import com.dragonevol.soar.key.KeyRegistry;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(Soar.MODID)
public class Soar
{
    public static final String MODID = "soar";

    // Directly reference a log4j logger.
    // dragon_RT: made public to allow testing.
    public static final Logger LOGGER = LogManager.getLogger();

    public Soar() {
        // dragon: register server config
        // why it only works when registering here??
        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.SERVER_SPEC);

        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);
    }

    private void setup(final FMLCommonSetupEvent event) {
        // some preinit code
        // dragon: register network packets
        Network.registerMessages();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        // dragon: register keybinding here
        KeyRegistry.registerKeyBindings();
    }
}
