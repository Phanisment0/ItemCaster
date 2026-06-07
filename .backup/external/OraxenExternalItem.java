package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import io.phanisment.itemcaster.util.Identifier;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;

public class OraxenExternalItem implements IExternalItem {
	@Override
	public String getPlugin() {
		return "oraxen";
	}

	@SuppressWarnings("null")
	@Override
	public ItemStack resolve(Identifier id, MythicItem item) {
		ItemBuilder ib = OraxenItems.getItemById(id.path());
		if (ib != null) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "Oraxen item not found: " + id.path());
			return null;
		}
		return ib.build();
	}
}