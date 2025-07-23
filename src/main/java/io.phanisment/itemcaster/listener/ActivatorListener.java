package io.phanisment.itemcaster.listener;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;

import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.block.BlockDamageAbortEvent;

import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerAnimationEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerToggleSneakEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.event.player.PlayerToggleSprintEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.util.ItemUtil;

public class ActivatorListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			ItemUtil.runSkill(player, Activator.RIGHT_CLICK);
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
		Player player = event.getPlayer();
		ItemUtil.runSkill(player, Activator.INTERACT_ENTITY);
	}
	
	@EventHandler
	public void onPlayerSwing(PlayerAnimationEvent e) {
		Player player = e.getPlayer();
		switch (e.getAnimationType()) {
			case ARM_SWING:
				ItemStack hand = player.getInventory().getItemInMainHand();
				if (ItemUtil.validateItem(hand)) {
					new SkillActivator(player, hand, Activator.LEFT_CLICK);
				}
				break;
			case OFF_ARM_SWING:
				ItemStack offHand = player.getInventory().getItemInOffHand();
				if (ItemUtil.validateItem(offHand)) {
					new SkillActivator(player, offHand, Activator.LEFT_CLICK);
				}
				break;
		}
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		ItemStack item = event.getItemDrop().getItemStack();
		if (ItemUtil.validateItem(item)) {
			new SkillActivator(player, item, Activator.DROP);
		}
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPickUp(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem().getItemStack();
		if (ItemUtil.validateItem(item)) {
			new SkillActivator(player, item, Activator.PICKUP);
		}
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player) {
			Player player = (Player)e.getEntity();
			ItemUtil.runSkill(player, Activator.DAMAGED);
		}
	}
	
	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player) {
			Player player = (Player)e.getDamager();
			ItemUtil.runSkill(player, Activator.ATTACK);
		}
	}
	
	@EventHandler
	public void onPlayerToggleSneak(PlayerToggleSneakEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.TOGGLE_SNEAK);
		if (e.isSneaking()) {
			ItemUtil.runSkill(player, Activator.SNEAK);
		} else {
			ItemUtil.runSkill(player, Activator.UNSNEAK);
		}
	}
	
	@EventHandler
	public void onPlayerConsume(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.CONSUME);
	}

	@EventHandler
	public void onPlayerShoot(EntityShootBowEvent e) {
		if(e.getEntity() instanceof Player player) {
			ItemStack item = e.getBow();
			if (ItemUtil.validateItem(item)) {
				SkillActivator skill = new SkillActivator(player, item, Activator.BOW_SHOOT);
				if (skill.getCancelEvent()) e.setCancelled(true);
			}
		}
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		Player player = e.getEntity();
		ItemUtil.runSkill(player, Activator.DEATH);
	}
	
	@EventHandler
	public void onLogin(PlayerLoginEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.LOGIN);
	}
	
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.QUIT);
	}
	
	@EventHandler
	public void onChangeSlot(PlayerItemHeldEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.CHANGE_SLOT);
	}
	
	@EventHandler
	public void onItemBreak(PlayerItemBreakEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getBrokenItem();
		if (ItemUtil.validateItem(item)) {
			new SkillActivator(player, item, Activator.ITEM_BREAK);
		}
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.FISHING);
	}
	
	@EventHandler
	public void onSprint(PlayerToggleSprintEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.TOGGLE_SPRINT);
		if (e.isSprinting()) {
			ItemUtil.runSkill(player, Activator.SPRINT);
		} else {
			ItemUtil.runSkill(player, Activator.UNSPRINT);
		}
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItemInHand();
		if (ItemUtil.validateItem(item)) {
			new SkillActivator(player, item, Activator.BLOCK_PLACE);
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.BLOCK_BREAK);
	}
	
	@EventHandler
	public void onBlockDamaged(BlockDamageEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItemInHand();
		if (ItemUtil.validateItem(item)) {
			new SkillActivator(player, item, Activator.BLOCK_DAMAGED);
		}
	}

	@EventHandler
	public void onBlockStopDamaged(BlockDamageAbortEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItemInHand();
		if (ItemUtil.validateItem(item)) {
			new SkillActivator(player, item, Activator.BLOCK_STOP_DAMAGED);
		}
	}

	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.TELEPORT);
	}
}