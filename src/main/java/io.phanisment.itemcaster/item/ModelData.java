package io.phanisment.itemcaster.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.logging.MythicLogger;

import io.phanisment.itemcaster.api.ApiHelper;
import io.phanisment.itemcaster.api.ExternalItemProvider;

import java.util.Optional;

public class ModelData {
	private Material type = Material.AIR;
	private int model_index = 0;
	
	private final MythicItem mi;
	private final MythicConfig config;
	
	public ModelData(String raw_item, MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();
		
		Optional<ItemStack> item = this.getItem(raw_item);
		if (item.isPresent()) {
			this.type = item.get().getType();
			ItemMeta meta = item.get().getItemMeta();
			if (meta.hasCustomModelData() && meta != null) this.model_index = meta.getCustomModelData();
		}
	}
	
	public Optional<ItemStack> getItem(String id) {
		if (id.isEmpty()) return Optional.empty();
		
		ItemStack item = new ItemStack(Material.AIR);
		if (id.contains(":")) {
			String[] parts = id.split(":");
			ExternalItemProvider eip = ApiHelper.registeredItems().get(parts[0]);
			if (ApiHelper.registeredItems().containsKey(parts[0])) {
				if (eip != null) return eip.resolve(parts, mi, config);
			} else {
				MythicLogger.errorItemConfig(mi, config, "Unknown External Item: " + parts[0]); 
			}
		} else {
			Material material = Material.valueOf(id.toUpperCase());
			item = new ItemStack(material);
		}
		return Optional.of(item);
	}
	
	public boolean isEmpty() {
		return model_index <= 0 && type == Material.AIR;
	}
	
	public Material getType() {
		return this.type;
	}
	
	public int getModel() {
		return this.model_index;
	}
}