package io.phanisment.itemcaster.skill.mechanic;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractPlayer;

public class AddFoodSaturationLevelMechanic implements ITargetedEntitySkill, INoTargetSkill {
	private float amount;

	public AddFoodSaturationLevelMechanic(MythicLineConfig mlc) {
		this.amount = mlc.getFloat(new String[] { "amount", "a" }, 1);
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity entity) {
		if (!entity.isPlayer()) return SkillResult.CONDITION_FAILED;
		AbstractPlayer player = entity.asPlayer();
		player.setFoodSaturation(player.getFoodSaturation() + this.amount);
		return SkillResult.SUCCESS;
	}

	@Override
	public SkillResult cast(SkillMetadata meta) {
		return this.castAtEntity(meta, meta.getCaster().getEntity());
	}
}