package io.phanisment.itemcaster.skill.mechanic;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractPlayer;

public class ReduceFoodLevelMechanic implements ITargetedEntitySkill {
	private PlaceholderInt amount;

	public ReduceFoodLevelMechanic(MythicLineConfig mlc) {
		this.amount = mlc.getPlaceholderInteger(new String[] { "amount", "a" }, 1);
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (!target.isPlayer()) return SkillResult.CONDITION_FAILED;
		AbstractPlayer player = target.asPlayer();
		player.setFoodLevel(player.getFoodLevel() - amount.get(target));
		return SkillResult.SUCCESS;
	}
}