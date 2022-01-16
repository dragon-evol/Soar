package com.dragonevol.soar.key;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import org.lwjgl.glfw.GLFW;

public class KeyRegistry {
    // dragon: the toggle elytra keybinding, coming from curios
    // WARNING WARNING WARNING: still have problem with localization
    public static KeyBinding TOGGLE_ELYTRA;
    public static KeyBinding ELYTRA_BOOST;

    // need to act when key is pressed, but not keep acting while holding key down
    public static boolean IS_HOLDING_TOGGLE_ELYTRA = false;
    public static int ELYTRA_BOOST_COOLDOWN = 0;

    public static void registerKeyBindings() {
        TOGGLE_ELYTRA = registerKeyBinding(new KeyBinding(
                "Toggle Elytra", GLFW.GLFW_KEY_R, "Soar"));
        // dragon: SHIT SHIT SHIT the localization just won't work whatever I write! Give up!
        /*
        TOGGLE_ELYTRA = registerKeyBinding(new KeyBinding(
                "key.soar.toggleelytra", GLFW.GLFW_KEY_R, "key.soar.category"));
         */
        ELYTRA_BOOST = registerKeyBinding(new KeyBinding(
                "Elytra Boost", GLFW.GLFW_KEY_X, "Soar"));
    }

    private static KeyBinding registerKeyBinding(KeyBinding keyBinding) {
        ClientRegistry.registerKeyBinding(keyBinding);
        return keyBinding;
    }
}
