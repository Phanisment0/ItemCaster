package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;
import io.phanisment.itemcaster.util.Identifier;
import net.momirealms.craftengine.bukkit.api.CraftEngineItems;
import net.momirealms.craftengine.core.item.CustomItem;
import net.momirealms.craftengine.core.util.Key;

public class CraftEngineExternalItem implements IExternalItem {

	@Override
	public String getPlugin() {
		return "craftengine";
	}

	@Override
	public ItemStack resolve(Identifier id, MythicItem item) {
		CustomItem<ItemStack> ci = CraftEngineItems.byId(new Key(id.namespace(), id.path()));
		if (ci.isEmpty()) {
			MythicLogger.errorItemConfig(item, item.getConfig(), "CraftEngine item not found: " + id);
			return null;
		}
		return ci.buildItemStack(1);
	}
}
