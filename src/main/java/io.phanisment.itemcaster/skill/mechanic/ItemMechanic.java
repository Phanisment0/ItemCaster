package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.bukkit.BukkitAdapter;

import java.util.Optional;

public abstract class ItemMechanic implements ITargetedEntitySkill, INoTargetSkill {
	private EquipmentSlot slot;
	
	public ItemMechanic(MythicLineConfig config) {
		String raw_slot = config.getString(new String[]{"slot", "s"}, "HAND");
		try {
			this.slot = EquipmentSlot.valueOf(raw_slot.toUpperCase());
		} catch (EnumConstantNotPresentException e) {
			
		}
	}
	
	@Override
	public SkillResult cast(SkillMetadata meta) {
		return this.castAtEntity(meta, meta.getCaster().getEntity());
	}
	
	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity entity) {
		LivingEntity target = (LivingEntity)BukkitAdapter.adapt(entity);
		ItemStack item = target.getEquipment().getItem(slot);
		Optional<ItemStack> result = this.resolve(target, item);
		if (result.isPresent()) {
			target.getEquipment().setItem(slot, result.get());
			return SkillResult.SUCCESS;
		}
		return SkillResult.CONDITION_FAILED;
	}
	
	protected EquipmentSlot getSlot() {
		return this.slot;
	}
	
	abstract Optional<ItemStack> resolve(LivingEntity target, ItemStack item);
}