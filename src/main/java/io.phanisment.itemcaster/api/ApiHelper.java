package io.phanisment.itemcaster.api;

import java.util.Map;
import java.util.HashMap;

public class ApiHelper {
	private static final Map<String, IExternalItem> external_item = new HashMap<>();
	
	public ApiHelper() {
		
	}
	
	public static void registerExternalItem(IExternalItem instance) {
		external_item.put(instance.getPlugin(), instance);
	}
	
	public static Map<String, IExternalItem> registeredExternalItems() {
		return external_item;
	}
}