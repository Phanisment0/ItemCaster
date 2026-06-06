package io.phanisment.itemcaster.skill.template;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.adapters.AbstractEntity;

import java.util.Optional;

public abstract class ItemMechanic implements ITargetedEntitySkill, INoTargetSkill {
	protected EquipmentSlot slot;

	public ItemMechanic(MythicLineConfig mlc) {
		String slot_string = mlc.getString(new String[] { "equipment", "e" }, "HAND").toUpperCase();
		try {
			this.slot = EquipmentSlot.valueOf(slot_string);
		} catch (IllegalArgumentException e) {
			MythicLogger.error("Invalid equipment slot value: " + slot_string, e);
		}
	}

	@Override
	public SkillResult cast(SkillMetadata meta) {
		return this.castAtEntity(meta, meta.getCaster().getEntity());
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity entity) {
		LivingEntity target = (LivingEntity) entity.getBukkitEntity();
		ItemStack item = target.getEquipment().getItem(slot);
		Optional<ItemStack> result = this.resolve(entity, item);
		if (result.isPresent()) {
			target.getEquipment().setItem(slot, result.get());
			return SkillResult.SUCCESS;
		}
		return SkillResult.CONDITION_FAILED;
	}

	protected EquipmentSlot getSlot() {
		return this.slot;
	}

	public abstract Optional<ItemStack> resolve(AbstractEntity target, ItemStack item);
}