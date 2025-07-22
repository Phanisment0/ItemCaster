package io.phanisment.itemcaster.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.events.MythicReloadedEvent;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.bukkit.events.MythicPlayerSignalEvent;
import io.lumine.mythic.bukkit.BukkitAdapter;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;

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
		this.runSkill(player, Activator.SIGNAL, event.getSignal());
	}
	
	private void runSkill(Player player, Activator type, String signal) {
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		if (SkillActivator.validateItem(mainHand)) {
			new SkillActivator(player, mainHand, type).setSignal(signal);
		}
		
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if (SkillActivator.validateItem(offHand)) {
			new SkillActivator(player, offHand, type).setSignal(signal);
		}
		
		ItemStack helmet = player.getInventory().getHelmet();
		if (SkillActivator.validateItem(helmet)) {
			new SkillActivator(player, helmet, type).setSignal(signal);
		}
		
		ItemStack chestplate = player.getInventory().getChestplate();
		if (SkillActivator.validateItem(chestplate)) {
			new SkillActivator(player, chestplate, type).setSignal(signal);
		}
		
		ItemStack leggings = player.getInventory().getLeggings();
		if (SkillActivator.validateItem(leggings)) {
			new SkillActivator(player, leggings, type).setSignal(signal);
		}
		
		ItemStack boots = player.getInventory().getBoots();
		if (SkillActivator.validateItem(boots)) {
			new SkillActivator(player, boots, type).setSignal(signal);
		}
	}
}