package io.phanisment.itemcaster.util.reflection;

import java.lang.reflect.Field;

public final class FieldUtil {
	private FieldUtil() {}

	public static Field findField(Class<?> clazz, String name) {
		Class<?> current = clazz;

		while (current != null) {
			try {
				Field field = current.getDeclaredField(name);
				field.setAccessible(true);
				return field;
			} catch (NoSuchFieldException ignored) {
				current = current.getSuperclass();
			}
		}

		return null;
	}

	public static void setField(Object instance, String name, Object value) {
		try {
			Field field = findField(instance.getClass(), name);

			if (field == null) throw new NoSuchFieldException(name);

			field.set(instance, value);
		} catch (Exception e) {
			throw new RuntimeException("Failed to set field " + name, e);
		}
	}

	public static Object getField(Object instance, String name) {
		try {
			Field field = findField(instance.getClass(), name);

			if (field == null) throw new NoSuchFieldException(name);

			return field.get(instance);
		} catch (Exception e) {
			throw new RuntimeException("Failed to get field " + name, e);
		}
	}
}