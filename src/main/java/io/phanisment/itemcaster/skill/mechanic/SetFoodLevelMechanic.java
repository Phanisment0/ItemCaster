package io.phanisment.itemcaster.skill.mechanic;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.api.adapters.AbstractEntity;

public class SetFoodLevelMechanic implements ITargetedEntitySkill {
	private final PlaceholderInt amount;

	public SetFoodLevelMechanic(MythicLineConfig mlc) {
		this.amount = mlc.getPlaceholderInteger(new String[] { "amount", "a" }, 1);
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (!target.isPlayer()) return SkillResult.CONDITION_FAILED;
		target.asPlayer().setFoodLevel(this.amount.get(target));
		return SkillResult.SUCCESS;
	}
}