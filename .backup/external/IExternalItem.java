package io.phanisment.itemcaster.item.external;

import org.bukkit.inventory.ItemStack;
import io.lumine.mythic.core.items.MythicItem;
import io.phanisment.itemcaster.util.Identifier;

public interface IExternalItem {
	String getPlugin();

	ItemStack resolve(Identifier id, MythicItem item);
}