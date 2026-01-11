package io.phanisment.itemcaster.item.external;

import java.util.Optional;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import net.momirealms.craftengine.bukkit.api.CraftEngineItems;
import net.momirealms.craftengine.core.item.CustomItem;
import net.momirealms.craftengine.core.util.Key;

public class CraftEngineExternalItem implements IExternalItem {

	@Override
	public String getPlugin() {
		return "craftengine";
	}

	@Override
	public Optional<ItemStack> resolve(String[] parts, MythicItem item) {
		CustomItem<ItemStack> ci = CraftEngineItems.byId(new Key(parts[1], parts[2]));
		if (ci.isEmpty()) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "CraftEngine item not found: " + parts[1] + ":" + parts[2]);
			return Optional.empty();
		}
		return Optional.of(ci.buildItemStack(1));
	}

}
