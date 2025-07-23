package io.phanisment.itemcaster.api;

import org.bukkit.inventory.ItemStack;
import java.util.Optional;

public interface ExternalItemProvider {
	String getPlugin();
	Optional<ItemStack> resolve(String[] parts);
}