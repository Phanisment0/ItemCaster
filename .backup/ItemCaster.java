package io.phanisment.itemcaster;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.bukkit.utils.plugin.LuminePlugin;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;
import io.phanisment.itemcaster.command.HandCasterToggleCommand;
import io.phanisment.itemcaster.command.ItemCasterCommand;
import io.phanisment.itemcaster.command.ItemCasterMenuCommand;
import io.phanisment.itemcaster.hand.HandCaster;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.listener.ActivatorListener;
import io.phanisment.itemcaster.listener.MythicListener;
import io.phanisment.itemcaster.listener.PaperListener;
import io.phanisment.itemcaster.listener.ProfileListener;
import io.phanisment.itemcaster.listener.ProfileRunnable;
import io.phanisment.itemcaster.mana_engine.ManaType;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.parser.ProgressBarParse;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.listener.CasterRunnable;
import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.registry.MythicPlaceholderRegistery;
import io.phanisment.itemcaster.item.external.CraftEngineExternalItem;
import io.phanisment.itemcaster.item.external.ItemsAdderExternalItem;
import io.phanisment.itemcaster.item.external.NexoExternalItem;
import io.phanisment.itemcaster.item.external.OraxenExternalItem;

public final class ItemCaster extends LuminePlugin {
	private static final Map<String, Runnable> compact = new HashMap<>();
	private static ItemCaster inst;
	private static MythicBukkit core;
	
	public ItemCaster() {
		inst = this;
	}

	@Override
	public void load() {
		core = MythicBukkit.inst();
		MythicPlaceholderRegistery.register();
	}

	@Override
	public void enable() {
		this.saveDefaultConfig();
		this.reload();

		// Register Compactbility
		compact.forEach((plugin, runnable) -> {
			if (hasPlugin(plugin)) {
				runnable.run();
				CasterLogger.send(plugin + " detected, Enabling the " + plugin + " features");
			}
		});
		if (ServerVersion.isPaper()) this.listen(new PaperListener());

		// Listeners
		this.listen(new ActivatorListener());
		this.listen(new MythicListener());
		this.listen(new ProfileListener());
		
		// Misc
		MenuManager.reload();

		new CasterRunnable().runTaskTimer(this, 1, 1);
		new ProfileRunnable().runTaskTimer(this, 1, 12000);
		this.registerCommand("itemcaster", new ItemCasterCommand(this));
		this.registerCommand("itemcastermenu", new ItemCasterMenuCommand(this));
		getCommand("handcastertoggle").setExecutor(new HandCasterToggleCommand());
		getCommand("handcastertoggle").setTabCompleter(new HandCasterToggleCommand());
	}

	@Override
	public void disable() {
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
		this.reloadConfig();
		HandCaster.load();
		
		Storage.debugging = this.getConfig().getBoolean("debug", false);
		Storage.prefix = this.getConfig().getString("prefix", "<gradient:#69DFFF:#5984CF>ItemCaster</gradient> | ");
		ProgressBarParse.TOTAL_BARS = this.getConfig().getInt("cooldown-bar.length", 20);
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

	static {
		compact.put("PlaceholderAPI", () -> {
			Storage.has_papi = true;
			new ItemCasterPlaceholderExpansion().register();
		});
		compact.put("ItemsAdder", () -> {
			Storage.has_itemsadder = true;
			ExternalItemRegistry.register(new ItemsAdderExternalItem());
		});
		compact.put("Nexo", () -> {
			Storage.has_nexo = true;
			ExternalItemRegistry.register(new NexoExternalItem());
		});
		compact.put("Oraxen", () -> {
			Storage.has_oraxen = true;
			ExternalItemRegistry.register(new OraxenExternalItem());
		});
		compact.put("CraftEngine", () -> {
			Storage.has_craftengine = true;
			ExternalItemRegistry.register(new CraftEngineExternalItem());
		});
		compact.put("AuraSkills", () -> {
			Storage.has_auraskills = true;
			Storage.mana_engine_type = ManaType.AURA_SKILLS;
		});
	}
}