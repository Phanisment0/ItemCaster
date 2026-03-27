package io.phanisment.itemcaster;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import fr.mrmicky.fastinv.FastInvManager;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.utils.plugin.LuminePlugin;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;
import io.phanisment.itemcaster.command.ItemCasterCommand;
import io.phanisment.itemcaster.command.ItemCasterMenuCommand;
import io.phanisment.itemcaster.config.ConfigData;
import io.phanisment.itemcaster.config.ConfigManager;
import io.phanisment.itemcaster.config.LanguageManager;
import io.phanisment.itemcaster.hand.HandCaster;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.listener.ActivatorListener;
import io.phanisment.itemcaster.listener.MythicListener;
import io.phanisment.itemcaster.listener.PaperListener;
import io.phanisment.itemcaster.listener.ProfileListener;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.listener.CasterRunnable;
import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.item.external.CraftEngineExternalItem;
import io.phanisment.itemcaster.item.external.ItemsAdderExternalItem;
import io.phanisment.itemcaster.item.external.NexoExternalItem;
import io.phanisment.itemcaster.item.external.OraxenExternalItem;
import io.phanisment.itemcaster.skill.SkillInjector;

public final class ItemCaster extends LuminePlugin {
	private static ItemCaster inst;
	private static MythicBukkit core;
	private LanguageManager lang_manager;

	public ItemCaster() {
		inst = this;
	}

	@Override
	public void load() {
		SkillInjector.register();
	}

	@Override
	public void enable() {
		new Metrics(this, Storage.id_bstats);
		core = MythicBukkit.inst();
		ConfigManager.load();
		HandCaster.load();
		Storage.debugging = config().debug_mode;
		this.lang_manager = new LanguageManager();
		this.lang_manager.load();
		

		String prefix = config().prefix;
		if (!prefix.isEmpty()) Storage.prefix = prefix;

		if (hasPlugin("PlaceholderAPI")) {
			Storage.has_papi = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_placeholderapi"));
			new ItemCasterPlaceholderExpansion().register();
		}
		if (hasPlugin("ItemsAdder")) {
			Storage.has_itemsadder = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_itemsadder"));
			ExternalItemRegistry.register(new ItemsAdderExternalItem());
		}
		if (hasPlugin("Nexo")) {
			Storage.has_nexo = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_nexo"));
			ExternalItemRegistry.register(new NexoExternalItem());
		}
		if (hasPlugin("Oraxen")) {
			Storage.has_oraxen = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_oraxen"));
			ExternalItemRegistry.register(new OraxenExternalItem());
		}
		if (hasPlugin("CraftEngine")) {
			Storage.has_craftengine = true;
			CasterLogger.send(LanguageManager.getString("has_plugin_craftengine"));
			ExternalItemRegistry.register(new CraftEngineExternalItem());
		}
		if (ServerVersion.isPaper()) this.listen(new PaperListener());

		new MenuManager();

		this.listen(new ActivatorListener());
		this.listen(new MythicListener());
		this.listen(new ProfileListener());
		FastInvManager.register(this);

		new CasterRunnable().runTaskTimer(this, 1L, 1L);
		this.registerCommand("itemcaster", new ItemCasterCommand(this));
		this.registerCommand("itemcastermenu", new ItemCasterMenuCommand(this));
	}

	@Override
	public void disable() {
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
		this.lang_manager.load();
		Storage.debugging = config().debug_mode;
		
		String prefix = config().prefix;
		if (!prefix.isEmpty()) Storage.prefix = prefix;

		CasterLogger.log("[<gradient:#69DFFF:#5984CF>ItemCaster</gradient>] <color:#23eb73>Plugin has finished reloading!");
	}

	public static ConfigData config() {
		return ConfigData.handler;
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