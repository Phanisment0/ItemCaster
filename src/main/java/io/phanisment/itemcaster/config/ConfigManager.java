package io.phanisment.itemcaster.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.lumine.mythic.api.packs.Pack;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.util.CasterLogger;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public final class ConfigManager {
	private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final File file_location = new File(ItemCaster.inst().getDataFolder(), "configs.json");
	//private static final RecipeManager recipe_manager = new RecipeManager();
	
	public static void load() {
		if (!ItemCaster.inst().getDataFolder().exists()) ItemCaster.inst().getDataFolder().mkdirs();
		if(!file_location.exists()) {
			save();
			return;
		}
		
		try (var reader = new FileReader(file_location)) {
			var load = gson.fromJson(reader, ConfigData.class);
			if (load != null) ConfigData.handler = load;
		} catch (IOException e) {
			e.printStackTrace();
		}
		/*
		CasterLogger.send("Loading Recipes...");
		for (Pack pack : ItemCaster.getPackManager().getPacks()) {
			File file = pack.getPackFolder("Recipes");
			if (!file.exists()) file.mkdirs();
			RecipeManager.load(file);
		}*/
	}
	
	public static void save() {
		try (var writer = new FileWriter(file_location)) {
			gson.toJson(ConfigData.handler, writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}