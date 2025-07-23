package io.phanisment.itemcaster.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.events.MythicReloadedEvent;
import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.bukkit.events.MythicPlayerSignalEvent;
import io.lumine.mythic.bukkit.BukkitAdapter;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.util.ItemUtil;

public class MythicListener implements Listener {
	
	@EventHandler 
	public void onReload(MythicReloadedEvent e) {
		ItemCaster.inst().reload();
	}
	
	@EventHandler
	public void onItemGenerate(MythicMobItemGenerateEvent e) {
		CasterItem item = new CasterItem(e.getItem());
		item.applyData(e);
	}
	
	@EventHandler
	public void onSignal(MythicPlayerSignalEvent event) {
		Player player = BukkitAdapter.adapt(event.getProfile().getEntity().asPlayer());
		ItemUtil.runSkill(player, Activator.SIGNAL, event.getSignal());
	}

	@EventHandler
	public void onConditionLoad(MythicConditionLoadEvent e) {
		switch (e.getConditionName().toLowerCase()) {
			case "attackcooldown":
				
				break;
		
			default:
				break;
		}
	}
}