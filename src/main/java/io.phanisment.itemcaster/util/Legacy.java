package io.phanisment.itemcaster.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.List;
import java.util.ArrayList;

/**
 * Utility class for converting Adventure components and MiniMessage strings
 * to legacy-formatted strings using a customized {@link LegacyComponentSerializer}.
 */
public class Legacy {
	/**
	 * A {@link LegacyComponentSerializer} instance that supports hex colors
	 * and unusual repeated character hex formatting.
	 */
	public static final LegacyComponentSerializer serializer = LegacyComponentSerializer.builder().hexColors().useUnusualXRepeatedCharacterHexFormat().build();
	
	/**
	 * A {@link MiniMessage} instance used to deserialize MiniMessage-formatted strings.
	 */
	public static final MiniMessage mm = MiniMessage.miniMessage();
	
	/**
	 * Private constructor to prevent instantiation of this utility class.
	 */
	private Legacy() {}
	
	/**
	 * Converts a MiniMessage-formatted string to a legacy-formatted string.
	 *
	 * @param text The MiniMessage-formatted string.
	 * @return The legacy-formatted string.
	 */
	public static String serializer(String text) {
		return serializer.serialize(mm.deserialize(text));
	}
	
	/**
	 * Converts a {@link Component} to a legacy-formatted string.
	 *
	 * @param component The component to convert.
	 * @return The legacy-formatted string.
	 */
	public static String serializer(Component component) {
		return serializer.serialize(component);
	}
	
	/**
	 * Converts a list of MiniMessage-formatted strings to a list of legacy-formatted strings.
	 *
	 * @param list The list of MiniMessage-formatted strings.
	 * @return A new list containing legacy-formatted strings.
	 */
	public static List<String> serializer(List<String> list) {
		List<String> new_list = new ArrayList<>();
		for (String line : list) {
			new_list.add(serializer(line));
		}
		return new_list;
	}
}
