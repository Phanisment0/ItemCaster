package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import java.util.Optional;

public class OraxenExternalItem implements IExternalItem {
	@Override
	public String getPlugin() {
		return "oraxen";
	}

	@Override
	public Optional<ItemStack> resolve(String[] parts, MythicItem item) {
		ItemBuilder ib = OraxenItems.getItemById(parts[1]);
		if (ib != null) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "Nexo item not found: " + parts[1]);
			return Optional.empty();
		}
		return Optional.empty();
	}
}