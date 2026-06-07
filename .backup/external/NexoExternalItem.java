package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import io.phanisment.itemcaster.util.Identifier;

import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;

public class NexoExternalItem implements IExternalItem {
	@Override
	public String getPlugin() {
		return "nexo";
	}

	@Override
	public ItemStack resolve(Identifier id, MythicItem item) {
		ItemBuilder ib = NexoItems.itemFromId(id.path());
		if (ib == null) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "Nexo item not found: " + id.path());
			return null;
		}
		return ib.build();
	}
}