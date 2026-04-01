package io.phanisment.itemcaster.util;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.Storage;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * WARNING: Do not use this class if the feature is contains user input.
 * 
 * Utility class for logging and messaging in the ItemCaster plugin.
 * Provides methods to send messages to players, the console, or other command senders,
 * with built-in support for prefixing and debug-level logging.
 */
public class CasterLogger {
	public static final MiniMessage MM = MiniMessage.miniMessage();
	private static final Logger LOGGER = ItemCaster.inst().getLogger();

	private CasterLogger() {
	}

	public static void log(String message) {
		Bukkit.getConsoleSender().sendMessage(MM.deserialize(message));
	}

	public static void log(String message, Object... params) {
		Bukkit.getConsoleSender().sendMessage(MM.deserialize(MessageFormat.format(message, params)));
	}
	
	/**
	 * Sends a message to the console with the plugin prefix.
	 *
	 * @param message The message to send.
	 */
	public static void send(String message) {
		Bukkit.getConsoleSender().sendMessage(MM.deserialize(Storage.prefix + message));
	}

	public static void send(String message, Object... params) {
		Bukkit.getConsoleSender().sendMessage(MM.deserialize(MessageFormat.format(Storage.prefix + message, params)));
	}
	
	/**
	 * Sends a message to a specific player with the plugin prefix.
	 *
	 * @param player  The player to send the message to.
	 * @param message The message to send.
	 */
	public static void send(Player player, String message) {
		player.sendMessage(MM.deserialize(Storage.prefix + message));
	}

	public static void send(Player player, String message, Object... params) {
		player.sendMessage(MM.deserialize(MessageFormat.format(Storage.prefix + message, params)));
	}

	/**
	 * Sends a message to a command sender (player, console, etc.) with the plugin prefix.
	 *
	 * @param sender  The command sender to send the message to.
	 * @param message The message to send.
	 */
	public static void send(CommandSender sender, String message) {
		sender.sendMessage(MM.deserialize(Storage.prefix + message));
	}

	public static void send(CommandSender sender, String message, Object... params) {
		sender.sendMessage(MM.deserialize(MessageFormat.format(Storage.prefix + message, params)));
	}

	/**
	 * Logs a message with SEVERE level if debug mode is enabled.
	 * Uses Java's default logger formatting placeholders: {0}, {1}, ...
	 *
	 * @param format The message format.
	 * @param values The values to insert into the format string.
	 */
	public static void error(String format, Object... values) {
		if (isDebugMode()) LOGGER.log(Level.SEVERE, "DEBUG | " + format, values);
	}
	
	/**
	 * Logs a message with WARNING level if debug mode is enabled.
	 * Uses Java's default logger formatting placeholders: {0}, {1}, ...
	 *
	 * @param format The message format.
	 * @param values The values to insert into the format string.
	 */
	public static void warn(String format, Object... values) {
		if (isDebugMode()) LOGGER.log(Level.WARNING, "DEBUG | " + format, values);
	}
	
	/**
	 * Logs a message with INFO level if debug mode is enabled.
	 * Uses Java's default logger formatting placeholders: {0}, {1}, ...
	 *
	 * @param format The message format.
	 * @param values The values to insert into the format string.
	 */
	public static void info(String format, Object... values) {
		if (isDebugMode()) LOGGER.log(Level.INFO, "DEBUG | " + format, values);
	}
	
	/**
	 * Checks if the plugin is currently in debug mode.
	 *
	 * @return True if debugging is enabled, false otherwise.
	 */
	private static boolean isDebugMode() {
		return Storage.debugging;
	}
}