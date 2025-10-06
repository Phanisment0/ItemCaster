package io.phanisment.itemcaster.skill.condition;

import org.bukkit.entity.Player;
import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.core.skills.SkillCondition;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

public abstract class ItemCondition extends SkillCondition implements IEntityCondition {
	private EquipmentSlot slot;
	
	public ItemCondition(String line, MythicLineConfig mlc) {
		super(line);
		String slot_string = mlc.getString(new String[]{"equipment", "e"}, "HAND").toUpperCase();
		try {
			this.slot = EquipmentSlot.valueOf(slot_string);
		} catch (IllegalArgumentException e) {
			MythicLogger.errorCondition(this, "Invalid equipment slot value: " + slot_string, e);
		}
	}
	
	@Override
	public boolean check(AbstractEntity e) {
		ItemStack item = ((LivingEntity)e.getBukkitEntity()).getEquipment().getItem(slot);
		if (!validateItem(item)) return false;
		return this.resolve(e, item);
	}
	
	abstract boolean resolve(AbstractEntity target, ItemStack item);
}
