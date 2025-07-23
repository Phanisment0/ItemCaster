package io.phanisment.itemcaster.support;

import com.nexomc.nexo.items.ItemBuilder;
import io.phanisment.itemcaster.api.ExternalItemProvider;

public class Nexo extends ExternalItemProvider {
	@Override
	public void String getPlugin() {
		return "nexo";
	}
	
	@Override
	public Optional<ItemStack> resolve(String[] parts) {
		ItemBuilder ib = NexoItems.itemFromId(parts[0]);
		if (ib != null) {
			i = ib.build();
		} else {
			MythicLogger.errorItemConfig(this.mi, this.config, "Nexo item not found: " + parts[0]);
		}
		return Optional.empty();
	}
}