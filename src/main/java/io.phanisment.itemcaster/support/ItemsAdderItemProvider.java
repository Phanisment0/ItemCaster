package io.phanisment.itemcaster.support;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import dev.lone.itemsadder.api.CustomStack;
import io.phanisment.itemcaster.api.IExternalItem;
import java.util.Optional;

public class ItemsAdderItemProvider implements IExternalItem {
	@Override
	public String getPlugin() {
		return "itemsadder";
	}
	
	@Override
	public Optional<ItemStack> resolve(String[] parts, MythicItem item, MythicConfig config) {
		String raw_id = parts[1] + ":" + parts[2];
		CustomStack cs = CustomStack.getInstance(raw_id);
		if (cs != null) {
			return Optional.of(cs.getItemStack());
		} else {
			MythicLogger.errorItemConfig(item, config, "ItemsAdder item not found: " + raw_id);
		}
		return Optional.empty();
	}
}