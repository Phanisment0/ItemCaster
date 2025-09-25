package io.phanisment.itemcaster.support;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;

import io.th0rgal.oraxen.api.OraxenItems;
import io.th0rgal.oraxen.items.ItemBuilder;
import io.phanisment.itemcaster.api.IExternalItem;
import java.util.Optional;

public class OraxenItemProvider implements IExternalItem {
	@Override
	public String getPlugin() {
		return "oraxen";
	}
	
	@Override
	public Optional<ItemStack> resolve(String[] parts, MythicItem item, MythicConfig config) {
		ItemBuilder ib = OraxenItems.getItemById(parts[1]);
		if (ib != null) {
			return Optional.of(ib.build());
		} else {
			MythicLogger.errorItemConfig(item, config, "Nexo item not found: " + parts[1]);
		}
		return Optional.empty();
	}
}