package io.phanisment.itemcaster.skill.condition;

import org.bukkit.entity.Player;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.skill.Skills;
import dev.aurelium.auraskills.api.user.SkillsUser;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.api.skills.conditions.ISkillMetaComparisonCondition;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.utils.numbers.RangedDouble;

public class AuraSkillLevelCondition implements IEntityCondition, ISkillMetaComparisonCondition {
	private final PlaceholderString skill;
	private final PlaceholderString level;

	public AuraSkillLevelCondition(MythicLineConfig config) {
		this.skill = config.getPlaceholderString(new String[]{ "skill", "s" }, "FIGHTING");
		this.level = config.getPlaceholderString(new String[]{ "level", "l" }, ">1");
	}

	@Override
	public boolean check(SkillMetadata meta, AbstractEntity target) {
		return this.check(target);
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean check(AbstractEntity target) {
		if (target.getBukkitEntity() instanceof Player player) {
			SkillsUser user = AuraSkillsApi.get().getUser(player.getUniqueId());
			Skills skill;
			try {
				skill = Skills.valueOf(this.skill.get(target));
			} catch (Exception e) {
				skill = Skills.FIGHTING;
			}
			return new RangedDouble(level.get(target)).equals(user.getSkillLevel(skill));
		}
		return false;
	}
}
