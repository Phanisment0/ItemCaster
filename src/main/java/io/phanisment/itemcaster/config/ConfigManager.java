package io.phanisment.itemcaster.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.phanisment.itemcaster.ItemCaster;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class ConfigManager {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final File file_location = new File(ItemCaster.inst().getDataFolder(), "configs.json");

	public static void load() {
		if (!ItemCaster.inst().getDataFolder().exists()) ItemCaster.inst().getDataFolder().mkdirs();
		if (!file_location.exists()) {
			save();
			return;
		}

		try (var reader = new FileReader(file_location)) {
			var load = gson.fromJson(reader, ConfigData.class);
			if (load != null) ConfigData.handler = load;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save() {
		try (var writer = new FileWriter(file_location)) {
			gson.toJson(ConfigData.handler, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}