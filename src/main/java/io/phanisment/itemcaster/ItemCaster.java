package io.phanisment.itemcaster;

import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.players.PlayerManager;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.items.ItemExecutor;
import io.lumine.mythic.api.packs.PackManager;
import io.lumine.mythic.api.mobs.MobManager;

import io.phanisment.itemcaster.api.ApiHelper;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.listener.ActivatorListener;
import io.phanisment.itemcaster.listener.MythicListener;
import io.phanisment.itemcaster.listener.CasterRunnable;
import io.phanisment.itemcaster.support.NexoItemProvider;
import io.phanisment.itemcaster.support.ItemsAdderItemProvider;
import io.phanisment.itemcaster.support.OraxenItemProvider;

public class ItemCaster extends JavaPlugin {
	private static ItemCaster inst;
	private static MythicBukkit core;
	
	public ItemCaster() {
		inst = this;
	}
		
	@Override
	public void onEnable() {
		if (hasPlugin("ItemsAdder")) {
			Constants.hasItemsAdder = true;
			CasterLogger.send("ItemsAdder detected, Enabling the ItemsAdder features.");
			ApiHelper.registerItem(new ItemsAdderItemProvider());
		}
		if (hasPlugin("Nexo")) {
			Constants.hasNexo = true;
			CasterLogger.send("Nexo detected, Enabling the Nexo features.");
			ApiHelper.registerItem(new NexoItemProvider());
		}
		if (hasPlugin("Oraxen")) {
			Constants.hasOraxen = true;
			CasterLogger.send("Oraxen detected, Enabling the Oraxen features.");
			ApiHelper.registerItem(new OraxenItemProvider());
		}
		
		core = MythicBukkit.inst();
		this.listen(new ActivatorListener());
		this.listen(new MythicListener());
		new CasterRunnable().runTaskTimer(this, 1L, 1L);
	}
	
	private boolean hasPlugin(String plugin) {
		return Bukkit.getPluginManager().getPlugin(plugin) != null || Bukkit.getPluginManager().isPluginEnabled(plugin);
	}
	
	private void listen(Listener event) {
		this.getServer().getPluginManager().registerEvents(event, this);
	}
	
	public void reload() {
		
	}
	
	public static ItemExecutor getItemManager() {
		return (ItemExecutor)core.getItemManager();
	}
	
	public static PackManager getPackManager() {
		return core.getPackManager();
	}
	
	public static SkillExecutor getSkillManager() {
		return core.getSkillManager();
	}
	
	public static PlayerManager getPlayerManager() {
		return core.getPlayerManager();
	}
	
	public static MobManager getMobManager() {
		return core.getMobManager();
	}
	
	public static ItemCaster inst() {
		return inst;
	}
	
	public static MythicBukkit core() {
		return core;
	}
}