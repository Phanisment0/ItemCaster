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
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.api.packs.PackManager;
import io.lumine.mythic.api.mobs.MobManager;

import io.phanisment.itemcaster.config.ConfigManager;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.listener.ActivatorListener;
import io.phanisment.itemcaster.listener.MythicListener;
import io.phanisment.itemcaster.listener.PaperListener;
import io.phanisment.itemcaster.menu.MenuInjector;
import io.phanisment.itemcaster.listener.CasterRunnable;
import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.item.external.ItemsAdderExternalItem;
import io.phanisment.itemcaster.item.external.NexoExternalItem;
import io.phanisment.itemcaster.item.external.OraxenExternalItem;
import io.phanisment.itemcaster.skill.SkillInjector;

public final class ItemCaster extends JavaPlugin {
	private static ItemCaster inst;
	private static MythicBukkit core;
	private static Metrics metrics;
	
	public ItemCaster() {
		inst = this;
	}
	
	@Override
	public void onLoad() {
		SkillInjector.register();
	}
	
	@Override
	public void onEnable() {
		core = MythicBukkit.inst();
		metrics = new Metrics(this, Constants.id_bstats);
		MenuInjector.register();
		
		if (hasPlugin("PlaceholderAPI")) {
			Constants.hasPAPI = true;
			CasterLogger.send("Found PlaceholderAPI, Register the ItemCaster placeholder");
			new ItemCasterPlaceholderExpansion().register();
		}
		if (hasPlugin("ItemsAdder")) {
			Constants.hasItemsAdder = true;
			CasterLogger.send("ItemsAdder detected, Enabling the ItemsAdder features.");
			ExternalItemRegistry.register(new ItemsAdderExternalItem());
		}
		if (hasPlugin("Nexo")) {
			Constants.hasNexo = true;
			CasterLogger.send("Nexo detected, Enabling the Nexo features.");
			ExternalItemRegistry.register(new NexoExternalItem());
		}
		if (hasPlugin("Oraxen")) {
			Constants.hasOraxen = true;
			CasterLogger.send("Oraxen detected, Enabling the Oraxen features.");
			ExternalItemRegistry.register(new OraxenExternalItem());
		}
		if (ServerVersion.isPaper()) {
			this.listen(new PaperListener());
		}
		
		ConfigManager.load();
		
		this.listen(new ActivatorListener());
		this.listen(new MythicListener());
		FastInvManager.register(this);
		
		new CasterRunnable().runTaskTimer(this, 1L, 1L);
	}
	
	@Override
	public void onDisable() {
		ConfigManager.save();
	}
	
	/**
	 * Check if plugin is enable or null.
	 * 
	 * @param plugin plugin name/id
	 * @return       true if plugin is enable
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
		return (ItemExecutor)core.getItemManager();
	}
	
	/**
	 * Shortcut for MythicBukkit.
	 * 
	 * @return the instance
	 */
	public static PackManager getPackManager() {
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
	public static MobManager getMobManager() {
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