package io.phanisment.itemcaster.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;

import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.item.external.IExternalItem;

import java.util.Optional;

/**
 * Utility Class for execute skill and get external item.
 */
public class ItemUtil {
	/**
	 * Get external item or basic ItemStack.
	 * 
	 * @param type id/type the item. if you want use external item, you must use like this `pluginname:itemid`.
	 * @return     the item or external item if the type is from external.
	 */
	public static ItemStack getItem(String type) {
		if (type == null || type.isBlank()) return new ItemStack(Material.STONE);
		if (type.contains(":")) {
			String[] parts = type.split(":");
			if (parts[0].toLowerCase().equalsIgnoreCase("mythicmobs")) {
				Optional<MythicItem> mi = MythicBukkit.inst().getItemManager().getItem(parts[1]);
				if (mi.isPresent()) return BukkitAdapter.adapt(mi.get().generateItemStack(1));
			}
			IExternalItem ei = ExternalItemRegistry.getRegistered(parts[0].toLowerCase());
			if (ei != null) return ei.resolve(parts, null).orElse(new ItemStack(Material.STONE));
		}
		
		try {
			return new ItemStack(Material.valueOf(type.toUpperCase()));
		} catch(IllegalArgumentException e) {
			return new ItemStack(Material.STONE);
		}
	}
	
	/**
	 * Validate item if not null.
	 * 
	 * @param item Item that want to validate.
	 * @return true if item not null.
	 */
	public static boolean validateItem(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
	public static void runSkill(Player player, Activator type) {
		PlayerInventory inv = player.getInventory();
		
		ItemStack mainHand = inv.getItemInMainHand();
		if (validateItem(mainHand)) new SkillActivator(player, mainHand, type);
		
		ItemStack offHand = inv.getItemInOffHand();
		if (validateItem(offHand)) new SkillActivator(player, offHand, type);
		
		for (ItemStack armor : inv.getArmorContents()) {
			if (validateItem(armor)) new SkillActivator(player, armor, type);
		}
	}
	
	public static void runSkill(Player player, Activator type, String signal) {
		PlayerInventory inv = player.getInventory();
		
		ItemStack mainHand = inv.getItemInMainHand();
		if (validateItem(mainHand)) new SkillActivator(player, mainHand, type).setSignal(signal);
		
		ItemStack offHand = inv.getItemInOffHand();
		if (validateItem(offHand)) new SkillActivator(player, offHand, type).setSignal(signal);
		
		for (ItemStack armor : inv.getArmorContents()) {
			if (validateItem(armor)) new SkillActivator(player, armor, type).setSignal(signal);
		}
	}
}