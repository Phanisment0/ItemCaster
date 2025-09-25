package io.phanisment.itemcaster.support;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import io.phanisment.itemcaster.api.IExternalItem;
import java.util.Optional;

public class NexoItemProvider implements IExternalItem {
	@Override
	public String getPlugin() {
		return "nexo";
	}
	
	@Override
	public Optional<ItemStack> resolve(String[] parts, MythicItem item, MythicConfig config) {
		ItemBuilder ib = NexoItems.itemFromId(parts[1]);
		if (ib != null) {
			return Optional.of(ib.build());
		} else {
			MythicLogger.errorItemConfig(item, config, "Nexo item not found: " + parts[1]);
		}
		return Optional.empty();
	}
}