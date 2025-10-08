package io.phanisment.itemcaster.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.logging.MythicLogger;

import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.skill.SkillActivator.Activator;
import io.phanisment.itemcaster.api.ApiHelper;
import io.phanisment.itemcaster.api.ExternalItemProvider;

import java.util.Optional;

public class ItemUtil {
	public static ItemStack getItem(String type) {
		ItemStack item = new ItemStack(Material.STONE);
		if (type.contains(":")) {
			String[] parts = type.split(":");
			String id = parts[0].toLowerCase();
			switch(id) {
				case "mythicmobs":
					Optional<MythicItem> mythicItem = MythicBukkit.inst().getItemManager().getItem(parts[1]);
					if (mythicItem.isPresent()) {
						MythicItem mi = mythicItem.get();
						item = BukkitAdapter.adapt(mi.generateItemStack(1));
					} else {
						MythicLogger.error("MythicMobs item not found: " + parts[1]);
					}
					break;
				default:
					ExternalItemProvider eip = ApiHelper.registeredExternalItems().get(id);
					if (ApiHelper.registeredExternalItems().containsKey(id)) {
						if (eip != null) item = eip.resolve(parts, null, null).get();
					} else {
						MythicLogger.error("Unknown external type: " + id);
					}
					break;
				}
		} else {
			Material material = Material.valueOf(type.toUpperCase());
			item = new ItemStack(material);
		}
		return item;
	}
	
	public static boolean validateItem(ItemStack item) {
		return item != null && item.getType() != Material.AIR;
	}
	
	public static void runSkill(Player player, Activator type) {
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		if (validateItem(mainHand)) {
			new SkillActivator(player, mainHand, type);
		}
		
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if (validateItem(offHand)) {
			new SkillActivator(player, offHand, type);
		}
		
		ItemStack helmet = player.getInventory().getHelmet();
		if (validateItem(helmet)) {
			new SkillActivator(player, helmet, type);
		}
		
		ItemStack chestplate = player.getInventory().getChestplate();
		if (validateItem(chestplate)) {
			new SkillActivator(player, chestplate, type);
		}
		
		ItemStack leggings = player.getInventory().getLeggings();
		if (validateItem(leggings)) {
			new SkillActivator(player, leggings, type);
		}
		
		ItemStack boots = player.getInventory().getBoots();
		if (validateItem(boots)) {
			new SkillActivator(player, boots, type);
		}
	}

	public static void runSkill(Player player, Activator type, String signal) {
		ItemStack mainHand = player.getInventory().getItemInMainHand();
		if (validateItem(mainHand)) {
			new SkillActivator(player, mainHand, type).setSignal(signal);
		}
		
		ItemStack offHand = player.getInventory().getItemInOffHand();
		if (validateItem(offHand)) {
			new SkillActivator(player, offHand, type).setSignal(signal);
		}
		
		ItemStack helmet = player.getInventory().getHelmet();
		if (validateItem(helmet)) {
			new SkillActivator(player, helmet, type).setSignal(signal);
		}
		
		ItemStack chestplate = player.getInventory().getChestplate();
		if (validateItem(chestplate)) {
			new SkillActivator(player, chestplate, type).setSignal(signal);
		}
		
		ItemStack leggings = player.getInventory().getLeggings();
		if (validateItem(leggings)) {
			new SkillActivator(player, leggings, type).setSignal(signal);
		}
		
		ItemStack boots = player.getInventory().getBoots();
		if (validateItem(boots)) {
			new SkillActivator(player, boots, type).setSignal(signal);
		}
	}
}