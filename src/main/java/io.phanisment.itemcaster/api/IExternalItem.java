package io.phanisment.itemcaster.api;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.items.MythicItem;

import java.util.Optional;

public interface IExternalItem {
	String getPlugin();
	Optional<ItemStack> resolve(String[] parts, MythicItem item, MythicConfig config);
}