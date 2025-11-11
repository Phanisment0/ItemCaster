package io.phanisment.itemcaster.config;
/*
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.config.recipe.RecipeData;
import io.phanisment.itemcaster.config.recipe.ShapedRecipeData;
import io.phanisment.itemcaster.config.recipe.ShapelessRecipeData;

import java.io.File;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.lang.reflect.Constructor;

public final class RecipeManager {
	private static Map<RecipeType, Class<? extends RecipeData<?>>> recipe_type = new HashMap<>();
	private static Map<String, Recipe> recipes = new HashMap<>();
	private static List<RecipeData<?>> recipe_cache = new ArrayList<>();
	
	static {
		recipe_type.put(RecipeType.SHAPED, ShapedRecipeData.class);
		recipe_type.put(RecipeType.SHAPELESS, ShapelessRecipeData.class);
	}
	
	public static void load(File file) {
		if (!recipes.isEmpty()) unload();
		var yaml = YamlConfiguration.loadConfiguration(file);
		for (String key : yaml.getKeys(false)) {
			var name_key = new NamespacedKey(ItemCaster.inst(), key);
			ConfigurationSection config = yaml.getConfigurationSection(key);
			RecipeType type;
			try {
				type = RecipeType.valueOf(config.getString("Type"));
			} catch (IllegalArgumentException e) {
				continue;
			}
			
			Class<? extends RecipeData<?>> data_class = recipe_type.get(type);
			if (data_class == null) continue;
			
			try {
				Constructor<? extends RecipeData<?>> inst = data_class.getDeclaredConstructor(NamespacedKey.class, ConfigurationSection.class);
				RecipeData<?> data = inst.newInstance(name_key, config);
				register(data);
			} catch (Exception e) {
				continue;
			}
		}
	}
	
	public static void register(RecipeData<?> data) {
		
	}
	
	public static void unload() {
		
	}
}
*/