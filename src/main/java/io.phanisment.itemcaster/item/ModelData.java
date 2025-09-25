package io.phanisment.itemcaster.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.logging.MythicLogger;

import io.phanisment.itemcaster.api.ApiHelper;
import io.phanisment.itemcaster.api.IExternalItem;

import java.util.Optional;
import java.util.Arrays;

public class ModelData {
	private Material type = Material.AIR;
	private int model_index = 0;
	
	private final MythicItem mi;
	private final MythicConfig config;
	private boolean has_id;
	private boolean has_material;
	
	public ModelData(String raw_item, MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();
		this.has_id = config.isSet("Id");
		this.has_material = config.isSet("Material");
		
		Optional<ItemStack> item = this.getItem(raw_item);
		if (item.isPresent()) {
			this.type = item.get().getType();
			ItemMeta meta = item.get().getItemMeta();
			if (meta.hasCustomModelData() && meta != null) this.model_index = meta.getCustomModelData();
		}
	}
	
	public Optional<ItemStack> getItem(String type) {
		if (type == null) return Optional.empty();
		
		ItemStack item = new ItemStack(Material.AIR);
		if (type.contains(":")) {
			if (has_id || has_material) {
				MythicLogger.errorItemConfig(mi, config, "You can not use Id/Material item when use the External Model Item, To fix try delete the Id/Material item."); 
				return Optional.empty();
			}
			
			String[] parts = type.split(":");
			String id = parts[0].toLowerCase();
			IExternalItem eip = ApiHelper.registeredExternalItems().get(id);
			if (ApiHelper.registeredExternalItems().containsKey(id)) {
				if (eip != null) return eip.resolve(parts, mi, config);
			} else {
				MythicLogger.errorItemConfig(mi, config, "Unknown External Item: " + id); 
			}
		} else {
			Material material = Material.valueOf(type.toUpperCase());
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