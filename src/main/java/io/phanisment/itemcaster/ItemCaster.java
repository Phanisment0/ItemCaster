package io.phanisment.itemcaster;

import org.bstats.bukkit.Metrics;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import fr.mrmicky.fastinv.FastInvManager;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;
import io.lumine.mythic.core.players.PlayerManager;
import io.lumine.mythic.core.items.ItemExecutor;
import io.lumine.mythic.core.mobs.MobExecutor;
import io.lumine.mythic.core.packs.PackExecutor;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.phanisment.itemcaster.command.ItemCasterCommand;
import io.phanisment.itemcaster.config.ConfigManager;
import io.phanisment.itemcaster.config.LanguageManager;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.listener.ActivatorListener;
import io.phanisment.itemcaster.listener.MythicListener;
import io.phanisment.itemcaster.listener.PaperListener;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.listener.CasterRunnable;
import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.item.external.CraftEngineExternalItem;
import io.phanisment.itemcaster.item.external.ItemsAdderExternalItem;
import io.phanisment.itemcaster.item.external.NexoExternalItem;
import io.phanisment.itemcaster.item.external.OraxenExternalItem;
import io.phanisment.itemcaster.skill.SkillInjector;

public final class ItemCaster extends JavaPlugin {
	private static ItemCaster inst;
	private static MythicBukkit core;

	public ItemCaster() {
		inst = this;
	}

	@Override
	public void onLoad() {
		SkillInjector.register();
	}

	@Override
	public void onEnable() {
		new Metrics(this, Constants.id_bstats);
		core = MythicBukkit.inst();
		new LanguageManager().load();

		if (hasPlugin("PlaceholderAPI")) {
			Constants.has_papi = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_placeholderapi"));
			new ItemCasterPlaceholderExpansion().register();
		}
		if (hasPlugin("ItemsAdder")) {
			Constants.has_itemsadder = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_itemsadder"));
			ExternalItemRegistry.register(new ItemsAdderExternalItem());
		}
		if (hasPlugin("Nexo")) {
			Constants.has_nexo = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_nexo"));
			ExternalItemRegistry.register(new NexoExternalItem());
		}
		if (hasPlugin("Oraxen")) {
			Constants.has_oraxen = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_oraxen"));
			ExternalItemRegistry.register(new OraxenExternalItem());
		}
		if (hasPlugin("CraftEngine")) {
			Constants.has_craftengine = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_craftengine"));
			ExternalItemRegistry.register(new CraftEngineExternalItem());
		}
		if (ServerVersion.isPaper()) this.listen(new PaperListener());

		ConfigManager.load();
		new MenuManager();

		this.listen(new ActivatorListener());
		this.listen(new MythicListener());
		FastInvManager.register(this);

		new CasterRunnable().runTaskTimer(this, 1L, 1L);
		this.getCommand("itemcaster").setExecutor(new ItemCasterCommand());
	}

	@Override
	public void onDisable() {
		ConfigManager.save();
	}

	/**
	 * Check if plugin is enable or null.
	 * 
	 * @param plugin plugin name/id
	 * @return true if plugin is enable
	 */
	private boolean hasPlugin(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin) != null || Bukkit.getPluginManager().isPluginEnabled(plugin);
	}

	/**
	 * Make register the listener/event more efficient.
	 * 
	 * @param event Class that implements Listener
	 */
	private void listen(Listener event) {
		this.getServer().getPluginManager().registerEvents(event, this);
	}

	/**
	 * Method to reload ItemCaster data and configuration.
	 */
	public void reload() {
		ConfigManager.load();
	}

	/**
	 * Shortcut for MythicBukkit.
	 * 
	 * @return the instance
	 */
	public static ItemExecutor getItemManager() {
		return (ItemExecutor) core.getItemManager();
	}

	/**
	 * Shortcut for MythicBukkit.
	 * 
	 * @return the instance
	 */
	public static PackExecutor getPackManager() {
		return core.getPackManager();
	}

	/**
	 * Shortcut for MythicBukkit.
	 * 
	 * @return the instance
	 */
	public static SkillExecutor getSkillManager() {
		return core.getSkillManager();
	}

	/**
	 * Shortcut for MythicBukkit.
	 * 
	 * @return the instance
	 */
	public static PlayerManager getPlayerManager() {
		return core.getPlayerManager();
	}

	/**
	 * Shortcut for MythicBukkit.
	 * 
	 * @return the instance
	 */
	public static MobExecutor getMobManager() {
		return core.getMobManager();
	}

	/**
	 * This instance
	 */
	public static ItemCaster inst() {
		return inst;
	}

	/**
	 * MythicMobs instance
	 */
	public static MythicBukkit core() {
		return core;
	}
}