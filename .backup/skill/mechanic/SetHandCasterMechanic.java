package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.entity.Player;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.Identifier;

public class SetHandCasterMechanic implements ITargetedEntitySkill, INoTargetSkill {
	private final Identifier id;

	public SetHandCasterMechanic(MythicLineConfig mlc) {
		this.id = new Identifier(mlc.getString(new String[]{"id", "i", "hand", "h"}, ""));
	}

	@Override
	public SkillResult cast(SkillMetadata meta) {
		return this.castAtEntity(meta, meta.getCaster().getEntity());
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (target.getBukkitEntity() instanceof Player player) {
			ProfileData data = ProfileManager.get(player).getData();
			data.hand_ability = id;
		}
		return SkillResult.SUCCESS;
	}
}
