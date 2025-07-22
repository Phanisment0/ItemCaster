package io.phanisment.itemcaster.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.Material;

import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.api.config.MythicConfig;

import dev.lone.itemsadder.api.CustomStack;
import io.th0rgal.oraxen.api.OraxenItems;
import com.nexomc.nexo.api.NexoItems;

import io.phanisment.itemcaster.Constants;

public class ModelData {
	private Material type = Material.AIR;
	private int model_index = 0;
	
	private final MythicItem mi;
	private final MythicConfig config;
	
	public ModelData(String raw_item, MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();
		
		ItemStack item = this.getItem(raw_item);
		if (item != null) {
			this.type = item.getType();
			this.model_index = item.getItemMeta().getCustomModelData();
		}
	}
	
	public ItemStack getItem(String id) {
		if (id == null) return null;
		
		ItemStack i = new ItemStack(Material.STONE);
		if (id.contains(":")) {
			String[] parts = id.split(":");
			switch(parts[0].toLowerCase()) {
				case "itemsadder":
				if (Constants.hasItemsAdder) {
					String raw_id = parts[1] + ":" + parts[2];
					CustomStack stack = CustomStack.getInstance(raw_id);
					if (stack != null) {
						i = stack.getItemStack();
					} else {
						MythicLogger.errorItemConfig(mi, config, "ItemsAdder item not found: " + raw_id);
					}
				}
				break;
			case "oraxen":
				if (Constants.hasOraxen) {
					io.th0rgal.oraxen.items.ItemBuilder ib = OraxenItems.getItemById(parts[1]);
					if (ib != null) {
						i = ib.build();
					} else {
						MythicLogger.errorItemConfig(mi, config, "Oraxen item not found: " + parts[1]);
					}
				}
				break;
			case "nexo":
				if (Constants.hasNexo) {
					com.nexomc.nexo.items.ItemBuilder ib = NexoItems.itemFromId(parts[1]);
					if (ib != null) {
						i = ib.build();
					} else {
						MythicLogger.errorItemConfig(mi, config, "Nexo item not found: " + parts[1]);
					}
				}
				break;
			default:
				MythicLogger.errorItemConfig(mi, config, "Unknown external type: " + parts[0].toLowerCase());
				break;
			}
		} else {
			Material material = Material.valueOf(id.toUpperCase());
			i = new ItemStack(material);
		}
		return i;
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