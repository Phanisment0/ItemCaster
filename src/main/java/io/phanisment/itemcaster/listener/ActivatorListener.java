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
import org.bukkit.event.entity.ProjectileHitEvent;

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
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.util.ItemUtil;

public class ActivatorListener implements Listener {
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) ItemUtil.runSkill(player, Activator.RIGHT_CLICK);
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
				ItemStack main = player.getInventory().getItemInMainHand();
				if (ItemUtil.validateItem(main)) new SkillActivator(player, main, Activator.LEFT_CLICK);
				break;
			case OFF_ARM_SWING:
				ItemStack off = player.getInventory().getItemInOffHand();
				if (ItemUtil.validateItem(off)) new SkillActivator(player, off, Activator.LEFT_CLICK);
				break;
		}
	}
	
	@EventHandler
	public void onPlayerDrop(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItemDrop().getItemStack();
		if (ItemUtil.validateItem(item)) new SkillActivator(player, item, Activator.DROP);
	}
	
	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerPickUp(PlayerPickupItemEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem().getItemStack();
		if (ItemUtil.validateItem(item)) new SkillActivator(player, item, Activator.PICKUP);
	}
	
	@EventHandler
	public void onPlayerDamaged(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player player) {
			ItemUtil.runSkill(player, Activator.DAMAGED);
			
			switch (e.getCause()) {
				case BLOCK_EXPLOSION:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_BLOCK_EXPLOSION);
					break;
				case CAMPFIRE:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_CAMPFIRE);
					break;
				case CONTACT:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_CONTACT);
					break;
				case CRAMMING:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_CRAMMING);
					break;
				case CUSTOM:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_CUSTOM);
					break;
				case DRAGON_BREATH:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_DRAGON_BREATH);
					break;
				case DROWNING:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_DROWNING);
					break;
				case ENTITY_ATTACK:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_ENTITY_ATTACK);
					break;
				case ENTITY_EXPLOSION:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_ENTITY_EXPLOSION);
					break;
				case ENTITY_SWEEP_ATTACK:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_ENTITY_SWEEP_ATTACK);
					break;
				case FALL:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_FALL);
					break;
				case FALLING_BLOCK:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_FALLING_BLOCK);
					break;
				case FIRE:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_FIRE);
					break;
				case FIRE_TICK:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_FIRE_TICK);
					break;
				case FLY_INTO_WALL:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_FLY_INTO_WALL);
					break;
				case FREEZE:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_FREEZE);
					break;
				case HOT_FLOOR:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_HOT_FLOOR);
					break;
				case KILL:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_KILL);
					break;
				case LAVA:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_LAVA);
					break;
				case LIGHTNING:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_LIGHTNING);
					break;
				case MAGIC:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_MAGIC);
					break;
				case POISON:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_POISON);
					break;
				case PROJECTILE:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_PROJECTILE);
					break;
				case SONIC_BOOM:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_SONIC_BOOM);
					break;
				case STARVATION:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_STARVATION);
					break;
				case SUFFOCATION:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_SUFFOCATION);
					break;
				case SUICIDE:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_SUICIDE);
					break;
				case THORNS:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_THORNS);
					break;
				case VOID:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_VOID);
					break;
				case WITHER:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_WITHER);
					break;
				case WORLD_BORDER:
					ItemUtil.runSkill(player, Activator.DAMAGED_BY_WORLD_BORDER);
					break;
				default:
					break;
			}
		}
	}
	
	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player player) ItemUtil.runSkill(player, Activator.ATTACK);
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
	public void onPlayerDeath(PlayerDeathEvent e) {
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
		if (ItemUtil.validateItem(item)) new SkillActivator(player, item, Activator.ITEM_BREAK);
	}
	
	@EventHandler
	public void onFish(PlayerFishEvent e) {
		Player player = e.getPlayer();
		
		ItemUtil.runSkill(player, Activator.FISHING);
		
		switch (e.getState()) {
			case BITE:
				ItemUtil.runSkill(player, Activator.FISH_BITE);
				break;
			case CAUGHT_ENTITY:
				ItemUtil.runSkill(player, Activator.FISH_CAUGHT_ENTITY);
				break;
			case CAUGHT_FISH:
				ItemUtil.runSkill(player, Activator.FISH_CAUGHT_FISH);
				break;
			case FAILED_ATTEMPT:
				ItemUtil.runSkill(player, Activator.FISH_FAILED_ATTEMPT);
				break;
			case FISHING:
				ItemUtil.runSkill(player, Activator.FISH_FISHING);
				break;
			case IN_GROUND:
				ItemUtil.runSkill(player, Activator.FISH_IN_GROUND);
				break;
			case REEL_IN:
				ItemUtil.runSkill(player, Activator.FISH_REEL_IN);
				break;
			default:
				break;
		}
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
			SkillActivator skill = new SkillActivator(player, item, Activator.BLOCK_PLACE);
			if (skill.getCancelEvent()) e.setCancelled(true);
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
		if (ItemUtil.validateItem(item)) new SkillActivator(player, item, Activator.BLOCK_DAMAGED);
	}
	
	@EventHandler
	public void onBlockStopDamaged(BlockDamageAbortEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItemInHand();
		if (ItemUtil.validateItem(item)) new SkillActivator(player, item, Activator.BLOCK_STOP_DAMAGED);
	}
	
	@EventHandler
	public void onTeleport(PlayerTeleportEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.TELEPORT);
		
		switch (e.getCause()) {
			case CHORUS_FRUIT:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_CHORUS_FRUIT);
				break;
			case COMMAND:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_COMMAND);
				break;
			case DISMOUNT:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_DISMOUNT);
				break;
			case END_GATEWAY:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_END_GATEWAY);
				break;
			case END_PORTAL:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_END_PORTAL);
				break;
			case ENDER_PEARL:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_ENDER_PEARL);
				break;
			case EXIT_BED:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_EXIT_BED);
				break;
			case NETHER_PORTAL:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_NETHER_PORTAL);
				break;
			case PLUGIN:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_PLUGIN);
				break;
			case SPECTATE:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_SPECTATE);
				break;
			case UNKNOWN:
				ItemUtil.runSkill(player, Activator.TELEPORTED_BY_UNKNOWN);
				break;
		}
	}
	
	@EventHandler
	public void onProjectileHit(ProjectileHitEvent e) {
		if (e.getEntity() instanceof Player player) ItemUtil.runSkill(player, Activator.PROJECTILE_HIT);
	}
	
	@EventHandler
	public void onSwapHand(PlayerSwapHandItemsEvent e) {
		Player player = e.getPlayer();
		ItemStack main = e.getMainHandItem();
		ItemStack off = e.getOffHandItem();
		if (ItemUtil.validateItem(main)) new SkillActivator(player, main, Activator.BLOCK_STOP_DAMAGED);
		if (ItemUtil.validateItem(off)) new SkillActivator(player, off, Activator.BLOCK_STOP_DAMAGED);
	}
	
	@EventHandler
	public void onChangeWorld(PlayerChangedWorldEvent e) {
		Player player = e.getPlayer();
		ItemUtil.runSkill(player, Activator.CHANGE_WORLD);
	}
}