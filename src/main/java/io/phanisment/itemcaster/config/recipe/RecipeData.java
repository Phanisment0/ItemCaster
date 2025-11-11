package io.phanisment.itemcaster.config.recipe;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.Recipe;
import org.bukkit.configuration.ConfigurationSection;

public abstract class RecipeData<T extends Recipe> {
	protected final NamespacedKey id;
	protected final ConfigurationSection config;
	
	public RecipeData(NamespacedKey id, ConfigurationSection config) {
		this.id = id;
		this.config = config;
	}
	
	public NamespacedKey getId() {
		return this.id;
	}
	
	public abstract T toRecipe();
}