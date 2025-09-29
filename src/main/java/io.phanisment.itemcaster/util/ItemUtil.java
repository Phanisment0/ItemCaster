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
import io.phanisment.itemcaster.api.ApiHelper;
import io.phanisment.itemcaster.api.IExternalItem;

import java.util.Optional;

public class ItemUtil {
	public static ItemStack getItem(String type) {
		if (type == null || type.isBlank()) return new ItemStack(Material.STONE);
		if (type.contains(":")) {
			String[] parts = type.split(":");
			if (parts[0].toLowerCase().equalsIgnoreCase("mythicmobs")) {
				Optional<MythicItem> mi = MythicBukkit.inst().getItemManager().getItem(parts[1]);
				if (mi.isPresent()) return BukkitAdapter.adapt(mi.get().generateItemStack(1));
			}
			IExternalItem eip = ApiHelper.registeredExternalItems().get(type);
			if (eip != null) return eip.resolve(parts, null, null).orElse(new ItemStack(Material.STONE));
		}
		
		try {
			return new ItemStack(Material.valueOf(type.toUpperCase()));
		} catch(IllegalArgumentException e) {
			return new ItemStack(Material.STONE);
		}
	}
	
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