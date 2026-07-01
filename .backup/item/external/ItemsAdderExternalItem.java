package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import dev.lone.itemsadder.api.CustomStack;
import java.util.Optional;

public class ItemsAdderExternalItem implements IExternalItem {
	@Override
	public String getPlugin() {
		return "itemsadder";
	}

	@Override
	public Optional<ItemStack> resolve(String[] parts, MythicItem item) {
		String raw_id = parts[1] + ":" + parts[2];
		CustomStack cs = CustomStack.getInstance(raw_id);
		if (cs == null) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "ItemsAdder item not found: " + raw_id);
			return Optional.empty();
		}
		return Optional.of(cs.getItemStack());
	}
}