package io.phanisment.itemcaster.listener;

import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.papermc.paper.event.player.PlayerShieldDisableEvent;
import io.papermc.paper.event.player.PlayerStopUsingItemEvent;
import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.destroystokyo.paper.event.player.PlayerElytraBoostEvent;
import com.destroystokyo.paper.event.player.PlayerPickupExperienceEvent;

import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.util.ItemUtil;

public class PaperListener implements Listener {
	@EventHandler
	public void onShieldDisable(PlayerShieldDisableEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.SHIELD_DISABLED);
	}

	@EventHandler
	public void onStopUseItem(PlayerStopUsingItemEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		if (ItemUtil.validateItem(item)) new SkillActivator(player, item, Activator.STOP_USE_ITEM);
	}

	@EventHandler
	public void onArmorChange(PlayerArmorChangeEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getNewItem();
		if (ItemUtil.validateItem(item)) new SkillActivator(player, item, Activator.ARMOR_CHANGE);
	}

	@EventHandler
	public void onElytraBoost(PlayerElytraBoostEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.ELYRA_BOOST);
	}

	@EventHandler
	public void onPickupExp(PlayerPickupExperienceEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.PICKUP_EXP);
	}
}