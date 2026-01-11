package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import com.nexomc.nexo.api.NexoItems;
import com.nexomc.nexo.items.ItemBuilder;
import java.util.Optional;

public class NexoExternalItem implements IExternalItem {
	@Override
	public String getPlugin() {
		return "nexo";
	}

	@Override
	public Optional<ItemStack> resolve(String[] parts, MythicItem item) {
		ItemBuilder ib = NexoItems.itemFromId(parts[1]);
		if (ib == null) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "Nexo item not found: " + parts[1]);
			return Optional.empty();
		}
		return Optional.of(ib.build());
	}
}