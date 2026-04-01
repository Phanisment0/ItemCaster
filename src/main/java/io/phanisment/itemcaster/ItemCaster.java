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
import io.phanisment.itemcaster.hand.HandCaster;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.listener.ActivatorListener;
import io.phanisment.itemcaster.listener.MythicListener;
import io.phanisment.itemcaster.listener.PaperListener;
import io.phanisment.itemcaster.listener.ProfileListener;
import io.phanisment.itemcaster.listener.ProfileRunnable;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.reflection.SkillInjector;
import io.phanisment.itemcaster.listener.CasterRunnable;
import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.item.external.CraftEngineExternalItem;
import io.phanisment.itemcaster.item.external.ItemsAdderExternalItem;
import io.phanisment.itemcaster.item.external.NexoExternalItem;
import io.phanisment.itemcaster.item.external.OraxenExternalItem;

public final class ItemCaster extends LuminePlugin {
	private static ItemCaster inst;
	private static MythicBukkit core;

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

		String prefix = config().prefix;
		if (!prefix.isEmpty()) Storage.prefix = prefix;

		if (hasPlugin("PlaceholderAPI")) {
			Storage.has_papi = true;
			CasterLogger.send("PlaceholderAPI detected, Enabling the PlaceholderAPI features");
			new ItemCasterPlaceholderExpansion().register();
		}
		if (hasPlugin("ItemsAdder")) {
			Storage.has_itemsadder = true;
			CasterLogger.send("ItemsAdder detected, Enabling the ItemsAdder features");
			ExternalItemRegistry.register(new ItemsAdderExternalItem());
		}
		if (hasPlugin("Nexo")) {
			Storage.has_nexo = true;
			CasterLogger.send("Nexo detected, Enabling the Nexo features");
			ExternalItemRegistry.register(new NexoExternalItem());
		}
		if (hasPlugin("Oraxen")) {
			Storage.has_oraxen = true;
			CasterLogger.send("Oraxen detected, Enabling the Oraxen features");
			ExternalItemRegistry.register(new OraxenExternalItem());
		}
		if (hasPlugin("CraftEngine")) {
			Storage.has_craftengine = true;
			CasterLogger.send("CraftEngine detected, Enabling the CraftEngine features");
			ExternalItemRegistry.register(new CraftEngineExternalItem());
		}
		if (ServerVersion.isPaper()) this.listen(new PaperListener());

		new MenuManager();

		this.listen(new ActivatorListener());
		this.listen(new MythicListener());
		this.listen(new ProfileListener());
		FastInvManager.register(this);

		new CasterRunnable().runTaskTimer(this, 1, 1);
		new ProfileRunnable().runTaskTimer(this, 1, 12000);
		this.registerCommand("itemcaster", new ItemCasterCommand(this));
		this.registerCommand("itemcastermenu", new ItemCasterMenuCommand(this));
	}

	@Override
	public void disable() {
		ConfigManager.save();
		ProfileManager.saveAll();
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