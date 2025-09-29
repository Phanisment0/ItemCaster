package io.phanisment.itemcaster.util;

import java.util.Map;
import java.util.HashMap;

// This is uses for parser configuration not for general code.
public final class MapSafe {
	private final Map<String, Object> map;
	
	public MapSafe(Map<String, Object> map) {
		this.map = map;
	}
	
	public boolean contains(String key) {
		return map.containsKey(key);
	}
	
	public boolean isEmpty() {
		return map.isEmpty();
	}
	
	public Object getRaw(String key) {
		return map.get(key);
	}
	
	public String getString(String key) {
		Object value = map.get(key);
		return value instanceof String s ? s : String.valueOf(value);
	}
	
	public float getFloat(String key) {
		Object value = map.get(key);
		if (value instanceof Number n) return n.floatValue();
		if (value instanceof String s) {
			try {
				return Float.parseFloat(s);
			} catch (NumberFormatException e) {}
		}
		return 0f;
	}
	
	public double getDouble(String key) {
		Object value = map.get(key);
		if (value instanceof Number n) return n.doubleValue();
		if (value instanceof String s) {
			try {
				return Double.parseDouble(s);
			} catch (NumberFormatException e) {}
		}
		return 0D;
	}
	
	public int getInteger(String key) {
		Object value = map.get(key);
		if (value instanceof Number n) return n.intValue();
		if (value instanceof String s) {
			try {
				return Integer.parseInt(s);
			} catch (NumberFormatException e) {}
		}
		return 0;
	}
	
	public boolean getBoolean(String key) {
		Object value = map.get(key);
		if (value instanceof Boolean b) return b;
		if (value instanceof String s) return Boolean.parseBoolean(s);
		return false;
	}
	
	@SuppressWarnings("unchecked")
	public Map<String, Object> getMap(String key) {
		Object value = map.get(key);
		if (value instanceof Map<?, ?> m) return (Map<String, Object>)m;
		return new HashMap<>();
	}
	
	public MapSafe getSafeMap(String key) {
		Map<String, Object> m = getMap(key);
		return m != null ? new MapSafe(m) : new MapSafe(new HashMap<>());
	}
}