package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.bukkit.BukkitAdapter;

import java.io.File;

public abstract class ItemMechanic extends SkillMechanic {
	private EquipmentSlot slot;
	
	public ItemMechanic(SkillExecutor manager, File file, String skill, MythicLineConfig config) {
		super(manager, file, skill, config);
		PlaceholderString raw_slot = config.getPlaceholderString(new String[]{"slot", "s"}, "HAND");
		try {
			this.slot = EquipmentSlot.valueOf(raw_slot.get().toUpperCase());
		} catch (IllegalArgumentException e) {
			this.slot = EquipmentSlot.HAND;
		}
	}
	
	@Override
	public boolean execute(SkillMetadata meta) {
		Entity entity = meta.getCaster().getEntity().getBukkitEntity();
		if (entity instanceof Player player) {
			ItemStack item = this.getItem();
			if (item != null) {
				player.getInventory().setItem(slot, item);
				return true;
			}
		}
		return false;
	}
	
	abstract ItemStack getItem();
}