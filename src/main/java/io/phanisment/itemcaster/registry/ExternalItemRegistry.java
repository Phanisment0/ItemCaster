package io.phanisment.itemcaster.registry;

import io.phanisment.itemcaster.item.external.IExternalItem;
import java.util.Map;
import java.util.HashMap;

public final class ExternalItemRegistry {
	private static final Map<String, IExternalItem> external_item = new HashMap<>();
	
	public static void register(IExternalItem instance) {
		external_item.put(instance.getPlugin().toLowerCase(), instance);
	}
	
	public static Map<String, IExternalItem> registered() {
		return new HashMap<>(external_item);
	}
	
	public static IExternalItem getRegistered(String plugin) {
		return external_item.get(plugin.toLowerCase());
	}
}