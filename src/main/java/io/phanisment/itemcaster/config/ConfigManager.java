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
	private static final String file_name = "configs.json";
	
	private static File file() {
		return new File(ItemCaster.inst().getDataFolder(), file_name);
	}
	
	public static void load() {
		var config_file = file();
		if (!config_file.getParentFile().exists()) config_file.getParentFile().mkdirs();
		if (!config_file.exists()) {
			ItemCaster.inst().saveResource(file_name, false);
			save();
			return;
		}
		
		try (var reader = new FileReader(config_file)) {
			var load = gson.fromJson(reader, ConfigData.class);
			if (load != null) ConfigData.handler = load;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void save() {
		try (var writer = new FileWriter(file())) {
			gson.toJson(ConfigData.handler, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}