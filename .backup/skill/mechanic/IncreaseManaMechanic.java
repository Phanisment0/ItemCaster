package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.entity.Player;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderDouble;
import io.phanisment.itemcaster.Storage;
import io.phanisment.itemcaster.mana_engine.ManaType;
import io.phanisment.itemcaster.util.CasterLogger;

public class IncreaseManaMechanic implements ITargetedEntitySkill {
	private final PlaceholderDouble amount;
	public IncreaseManaMechanic(MythicLineConfig config) {
		this.amount = config.getPlaceholderDouble(new String[]{ "amount", "a" }, 1.0);
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (target.getBukkitEntity() instanceof Player player) {
			if (Storage.mana_engine_type == ManaType.AURA_SKILLS) {
				SkillsUser user = AuraSkillsApi.get().getUser(player.getUniqueId());
				user.setMana(user.getMana() + amount.get(target));
			} else CasterLogger.send(player, "<red>You dont have plugin that has Mana Engine.");
		}
		return SkillResult.INVALID_TARGET;
	}
}
