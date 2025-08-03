package gg.zr0x8.nightvision;

import com.google.gson.Gson;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Config {
    private static final Path config_dir = Paths.get(MinecraftClient.getInstance().runDirectory.getPath() + "/config");
    private static final Path config_file = Paths.get(config_dir + "/night-vision.json");
    private static ConfigData config_data;

    public static ConfigData getConfigData() {
        if (config_data != null) return config_data;

        try {
            if (!Files.exists(config_file)) {
                Files.createDirectories(config_dir);
                Files.createFile(config_file);
                config_data = ConfigData.getDefault();
                config_data.save();
                return config_data;
            }
        } catch (IOException e) {
            e.printStackTrace();
            config_data = ConfigData.getDefault();
            return config_data;
        }
        try {
            Gson gson = new Gson();
            FileReader reader = new FileReader(config_file.toFile());
            config_data = gson.fromJson(reader, ConfigData.class);
        } catch (IOException e) {
            e.printStackTrace();
            config_data = ConfigData.getDefault();
        }
        return config_data;
    }

    public static Screen createConfigScreen(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create().setParentScreen(parent).setTitle(Text.of("night vision"));

        ConfigCategory general = builder.getOrCreateCategory(Text.of("general configuration"));

        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        general.addEntry(entryBuilder.startBooleanToggle(Text.of("u want no blind?"), config_data.no_blindness)
                .setDefaultValue(false)
                .setYesNoTextSupplier(value->value
                        ? Text.literal("yh bro").formatted(Formatting.GREEN)
                        : Text.literal("hell na").formatted(Formatting.RED))
                .setSaveConsumer(newValue -> config_data.no_blindness = newValue).build());

        builder.setSavingRunnable(config_data::save);

        return builder.build();
    }

    public static class ConfigData {
        public boolean no_blindness;

        public ConfigData(boolean no_blindness) {
            this.no_blindness = no_blindness;
        }

        public static ConfigData getDefault() {
            return new ConfigData(false);
        }

        public void save() {
            try {
                Gson gson = new Gson();
                FileWriter writer = new FileWriter(config_file.toFile());
                gson.toJson(this, writer);
                writer.flush();
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
