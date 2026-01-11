package io.phanisment.itemcaster.skill.mechanic;

import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractPlayer;

import java.io.File;

@MythicMechanic(author = "Phanisment", name = "setfoodsaturationlevel", aliases = {
		"setfoodsaturation" }, description = "Set player food saturation level")
public class SetFoodSaturationLevelMechanic extends SkillMechanic implements ITargetedEntitySkill {
	private float amount;

	public SetFoodSaturationLevelMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		this.amount = mlc.getFloat(new String[] { "amount", "a" }, 1);
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity entity) {
		if (!entity.isPlayer())
			return SkillResult.CONDITION_FAILED;
		AbstractPlayer player = entity.asPlayer();
		player.setFoodSaturation(this.amount);
		return SkillResult.SUCCESS;
	}
}