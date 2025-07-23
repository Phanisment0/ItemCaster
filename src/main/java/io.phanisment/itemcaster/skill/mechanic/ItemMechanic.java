package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.PlayerInventory;

import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.api.skills.SkillResult;

import java.io.File;

public abstract class ItemMechanic extends SkillMechanic {
	private final EquipmentSlot slot;
	
	public ItemMechanic(SkillExecutor manager, File file, String skill, MythicLineConfig config) {
		super(manager, file, skill, config);
		PlaceholderString raw_slot = config.getPlaceholderString(new String[]{"slot", "s"}, "HAND").toUpperCase();
		try {
			this.slot = EquipmentSlot.valueOf(raw_slot);
		} catch (IllegalArgumentException e) {
			this.slot = EquipmentSlot.HAND;
		}
	}
	
	@Override
	public SkillResult cast(SkillMetadata meta) {
		Entity entity = meta.getCaster().getEntity().getBukkitEntity();
		if (entity instanceof Player player) {
			ItemStack item = this.getItem();
			if (item != null) {
				player.getInventory().setItem(slot, item);
				return SkillResult.SUCCESS;
			}
		}
		return SkillResult.INVALID_TARGET;
	}
	
	abstract ItemStack getItem();
}