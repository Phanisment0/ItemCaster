package io.phanisment.itemcaster.api;

import java.util.Map;
import java.util.HashMap;

public class ApiHelper {
	private static final Map<String, ExternalItemProvider> external_item = new HashMap<>();
	
	public ApiHelper() {
		
	}
	
	public static void registerItem(ExternalItemProvider instance) {
		external_item.put(instance.getPlugin(), instance);
	}
	
	public static Map<String, ExternalItemProvider> registeredItems() {
		return new HashMap<>(external_item);
	}
}