package io.phanisment.itemcaster.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;

import java.io.File;

import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.events.MythicReloadedEvent;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.bukkit.events.MythicPlayerSignalEvent;
import io.lumine.mythic.bukkit.events.MythicConditionLoadEvent;
import io.lumine.mythic.bukkit.events.MythicMechanicLoadEvent;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.skill.condition.*;
import io.phanisment.itemcaster.skill.mechanic.GetItemMechanic;
import io.phanisment.itemcaster.util.ItemUtil;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.bukkit.BukkitAdapter;

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
	public void onSignal(MythicPlayerSignalEvent e) {
		Player player = BukkitAdapter.adapt(e.getProfile().getEntity().asPlayer());
		ItemUtil.runSkill(player, Activator.SIGNAL, e.getSignal());
	}
	
	@EventHandler
	public void onConditionLoad(MythicConditionLoadEvent e) {
		switch(e.getConditionName().toLowerCase()) {
			case "attackcooldown":
				e.register(new AttackCooldownCondition());
				break;
			default:
				break;
		}
	}
	
	@EventHandler
	public void onMechanicLoad(MythicMechanicLoadEvent e) {
		MythicLineConfig config = e.getConfig();
		File file = new File(config.getFileName());
		SkillExecutor manager = ItemCaster.getSkillManager();
		String line = config.getLine(); 
		switch(e.getMechanicName().toLowerCase()) {
			case "getItem":
				e.register(new GetItemMechanic(manager, file, line, config));
				break;
			default:
				break;
		}
	}
}