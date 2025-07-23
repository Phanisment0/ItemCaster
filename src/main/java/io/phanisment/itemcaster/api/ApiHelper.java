package io.phanisment.itemcaster.api;

import java.util.Map;
import java.util.HashMap;

public class ApiHelper {
	private static final Map<String, ExternalItemProvider> external_item = new HashMap<>();
	
	public ApiHelper() {
		
	}
	
	public void registerItem(ExternalItemProvider instance) {
		external_item.add(instance.getPlugin(), instance);
	}
	
	public Map<String, ExternalItemProvider> registeredItems() {
		return external_item.clone();
	}
}