package io.phanisment.itemcaster.skill.mechanic;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderFloat;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractPlayer;

public class ReduceFoodSaturationLevelMechanic implements ITargetedEntitySkill {
	private PlaceholderFloat amount;

	public ReduceFoodSaturationLevelMechanic(MythicLineConfig mlc) {
		this.amount = mlc.getPlaceholderFloat(new String[] { "amount", "a" }, 1.0f);
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (!target.isPlayer()) return SkillResult.CONDITION_FAILED;
		AbstractPlayer player = target.asPlayer();
		player.setFoodSaturation(player.getFoodSaturation() - this.amount.get(target));
		return SkillResult.SUCCESS;
	}
}