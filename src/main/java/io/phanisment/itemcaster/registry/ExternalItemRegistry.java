package io.phanisment.itemcaster.registry;

import io.phanisment.itemcaster.item.external.IExternalItem;
import java.util.Map;
import java.util.HashMap;

/**
 * Registry for external item(other plugin item).
 * 
 * This is uses for other plugin dev to register their custom items.
 */
public final class ExternalItemRegistry {
	private static final Map<String, IExternalItem> external_item = new HashMap<>();
	
	/**
	 * Register your implemented class IExternalItem in here.
	 * 
	 * @param instance New instance of implemented IExternalItem class
	 */
	public static void register(IExternalItem instance) {
		external_item.put(instance.getPlugin().toLowerCase(), instance);
	}
	
	/**
	 * Get all list of registered IExternalItem.
	 * 
	 * @return list of registered item
	 */
	public static Map<String, IExternalItem> registered() {
		return new HashMap<>(external_item);
	}
	
	/**
	 * Get single instance of registered IExternalItem.
	 * 
	 * @param plugin Name of plugin
	 * @return the instance
	 */
	public static IExternalItem getRegistered(String plugin) {
		return external_item.get(plugin.toLowerCase());
	}
}