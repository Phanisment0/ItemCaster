package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import io.phanisment.itemcaster.util.Identifier;
import dev.lone.itemsadder.api.CustomStack;

public class ItemsAdderExternalItem implements IExternalItem {
	@Override
	public String getPlugin() {
		return "itemsadder";
	}

	@Override
	public ItemStack resolve(Identifier id, MythicItem item) {
		String raw_id = id.toString();
		CustomStack cs = CustomStack.getInstance(raw_id);
		if (cs == null) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "ItemsAdder item not found: " + raw_id);
			return null;
		}
		return null;
	}
}