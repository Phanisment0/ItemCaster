package io.phanisment.itemcaster.skill.mechanic;

import java.io.File;

import org.bukkit.entity.Player;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.INoTargetSkill;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;

@MythicMechanic(author = "Phanisment", name = "removehandcaster")
public class RemoveHandCasterMechanic extends SkillMechanic implements ITargetedEntitySkill, INoTargetSkill {
	public RemoveHandCasterMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
	}

	@Override
	public SkillResult cast(SkillMetadata meta) {
		return this.castAtEntity(meta, meta.getCaster().getEntity());
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (target.getBukkitEntity() instanceof Player player) {
			ProfileData data = ProfileManager.get(player).getData();
			data.hand_ability = null;
		}
		return SkillResult.SUCCESS;
	}
}
