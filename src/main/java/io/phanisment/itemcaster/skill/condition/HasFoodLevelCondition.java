package io.phanisment.itemcaster.skill.condition;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.api.skills.conditions.ISkillMetaComparisonCondition;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.utils.numbers.RangedInt;
import io.lumine.mythic.core.skills.SkillCondition;
import io.lumine.mythic.core.skills.placeholders.PlaceholderMeta;
import io.lumine.mythic.core.utils.annotations.MythicCondition;

@MythicCondition(author = "Phanisment", name = "hasfoodlevel", aliases = { "foodlevel",
		"hasfood" }, description = "If target attack is cooldown")
public class HasFoodLevelCondition extends SkillCondition implements IEntityCondition, ISkillMetaComparisonCondition {
	private PlaceholderString amount;

	public HasFoodLevelCondition(String line, MythicLineConfig mlc) {
		super(line);
		this.amount = mlc.getPlaceholderString(new String[] { "amount", "a", "food", "f" }, "0", this.conditionVar);
	}

	@Override
	public boolean check(AbstractEntity e) {
		if (!e.isPlayer())
			return false;
		return new RangedInt(this.amount.get(e)).equals(e.asPlayer().getFoodLevel());
	}

	@Override
	public boolean check(SkillMetadata meta, AbstractEntity e) {
		if (!e.isPlayer())
			return false;
		return new RangedInt(this.amount.get((PlaceholderMeta) meta, e)).equals(e.asPlayer().getFoodLevel());
	}
}