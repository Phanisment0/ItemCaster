package io.phanisment.itemcaster.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.events.MythicReloadedEvent;
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
		String skill = e.getMechanicName().toLowerCase();
		MythicLineConfigImpl config = (MythicLineConfigImpl)e.getConfig();
		switch(skill) {
			case "setitem":
				e.register(new SetItemMechanic(config));
				break;
			case "settypeitem":
				e.register(new SetTypeItemMechanic(config));
				break;
			case "setmodeldataitem":
				e.register(new SetModelDataItemMechanic(config));
				break;
			case "setamountitem":
				e.register(new SetAmountItemMechanic(config));
				break;
			case "setdurabiliyitem":
				e.register(new SetDurabilityItemMechanic(config));
				break;
			case "addamountitem":
				e.register(new AddAmountItemMechanic(config));
				break;
			case "addenchantmentitem":
				e.register(new AddEnchantmentItemMechanic(config));
				break;
			case "adddurabiliyitem":
				e.register(new AddDurabilityItemMechanic(config));
				break;
			case "decreasedurabiliyitem":
				e.register(new DecreaseDurabilityItemMechanic(config));
				break;
			case "mergenbtitem":
				e.register(new MergeNbtItemMechanic(config));
				break;
			case "removeenchantmentitem":
				e.register(new RemoveEnchantmentItemMechanic(config));
				break;
			case "resetenchantmentitem":
				e.register(new ResetEnchantmentItemMechanic(config));
				break;
			default:
				break;
		}
	}
}