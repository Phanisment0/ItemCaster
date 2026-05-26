package io.phanisment.itemcaster.skill.condition;

import org.bukkit.entity.Player;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.api.skills.conditions.ISkillMetaComparisonCondition;
import io.lumine.mythic.core.skills.SkillCondition;
import io.lumine.mythic.core.utils.annotations.MythicCondition;
import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.Identifier;

@MythicCondition(author = "Phanisment", name = "hasfoodlevel", aliases = { "foodlevel", "hasfood" }, description = "If target attack is cooldown")
public class HasHandCasterCondition extends SkillCondition implements IEntityCondition, ISkillMetaComparisonCondition {
	private final Identifier id;

	public HasHandCasterCondition(String line, MythicLineConfig mlc) {
		super(line);
		this.id = new Identifier(mlc.getString(new String[]{"id", "i", "hand", "h"}, ""));
	}

	@Override
	public boolean check(SkillMetadata meta, AbstractEntity target) {
		return this.check(target);
	}

	@Override
	public boolean check(AbstractEntity target) {
		if (target.getBukkitEntity() instanceof Player player) {
			ProfileData data = ProfileManager.get(player).getData();
			return data.hand_ability == id;
		}
		return false;
	}
}
