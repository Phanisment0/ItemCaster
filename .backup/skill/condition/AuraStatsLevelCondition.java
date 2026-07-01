package io.phanisment.itemcaster.skill.condition;

import org.bukkit.entity.Player;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.stat.Stats;
import dev.aurelium.auraskills.api.user.SkillsUser;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.api.skills.conditions.ISkillMetaComparisonCondition;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.utils.numbers.RangedDouble;

public class AuraStatsLevelCondition implements IEntityCondition, ISkillMetaComparisonCondition {
	private final PlaceholderString stats;
	private final PlaceholderString level;

	public AuraStatsLevelCondition(MythicLineConfig config) {
		this.stats = config.getPlaceholderString(new String[]{ "stats", "s" }, "STRENGTH");
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
			Stats stats;
			try {
				stats = Stats.valueOf(this.stats.get(target));
			} catch (Exception e) {
				stats = Stats.STRENGTH;
			}
			return new RangedDouble(level.get(target)).equals(user.getStatLevel(stats));
		}
		return false;
	}
}
