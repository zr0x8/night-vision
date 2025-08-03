package gg.zr0x8.nightvision;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;

import net.minecraft.util.Formatting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.lwjgl.glfw.GLFW;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;

@Environment(EnvType.CLIENT)

public class NightVisionClient implements ClientModInitializer {
    public static final Logger logger = LoggerFactory.getLogger("night-vision");
    private static KeyBinding toggle_key;
    public static Config.ConfigData config_data;
    @Override
    public void onInitializeClient() {
        logger.info("hi gang!");
        config_data = Config.getConfigData();

        toggle_key = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "toggle night vision",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_UNKNOWN,
                "night vision"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggle_key.wasPressed()) {
                Config.ConfigData cfg = Config.getConfigData();
                cfg.no_blindness = !cfg.no_blindness;
                cfg.save();

                if (client.player != null) {
                    Text prefix = Text.literal("[night vision] ").styled(style -> style.withColor(Formatting.LIGHT_PURPLE));
                    Text state = Text.literal(cfg.no_blindness ? "enabled" : "disabled")
                            .styled(style -> style.withColor(cfg.no_blindness ? Formatting.GREEN : Formatting.RED));
                    client.player.sendMessage(prefix.copy().append(state), false);
                }
            }
        });
    }
}
