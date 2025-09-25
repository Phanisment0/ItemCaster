package io.phanisment.itemcaster.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.events.MythicReloadedEvent;
import io.lumine.mythic.bukkit.events.MythicPostReloadedEvent;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.bukkit.events.MythicPlayerSignalEvent;
import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.lumine.mythic.core.config.MythicLineConfigImpl;
import io.lumine.mythic.bukkit.BukkitAdapter;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.skill.condition.*;
import io.phanisment.itemcaster.skill.mechanic.*;
import io.phanisment.itemcaster.util.ItemUtil;

import java.io.File;

public class MythicListener implements Listener {
	
	@EventHandler 
	public void onReload(MythicPostReloadedEvent e) {
		ItemCaster.inst().reload();
	}
	
	@EventHandler(priority=EventPriority.HIGH)
	public void onItemGenerate(MythicMobItemGenerateEvent e) {
		CasterItem item = new CasterItem(e.getItem());
		item.applyData(e);
	}
	
	@EventHandler
	public void onSignal(MythicPlayerSignalEvent e) {
		Player player = BukkitAdapter.adapt(e.getProfile().getEntity().asPlayer());
		ItemUtil.runSkill(player, Activator.SIGNAL, e.getSignal());
	}
}