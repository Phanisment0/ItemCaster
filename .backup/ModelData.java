package io.phanisment.itemcaster.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import io.lumine.mythic.core.items.MythicItem;

import io.phanisment.itemcaster.util.Identifier;

public class ModelData {
	private Material type = null;
	private Integer model_index = null;
	public ModelData(Identifier id) {
		this(id, null);
	}

	public ModelData(Identifier id, MythicItem mi) {
		ItemStack item = new ItemProvider(id, mi).get(false);
		if (item == null) return;

		this.type = item.getType();
		var meta = item.getItemMeta();
		if (meta.hasCustomModelData()) this.model_index = meta.getCustomModelData();
	}

	public void applyModel(ItemStack item) {
		if (type != null) item.withType(type);
		var meta = item.getItemMeta();
		if (model_index != null) meta.setCustomModelData(model_index);
	}

	public boolean hasItemModel() {
		return type == null || model_index == null;
	}
}