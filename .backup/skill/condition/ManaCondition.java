package io.phanisment.itemcaster.skill.condition;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.api.skills.conditions.ISkillMetaComparisonCondition;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.utils.numbers.RangedDouble;
import io.phanisment.itemcaster.Storage;
import io.phanisment.itemcaster.mana_engine.ManaType;
import io.phanisment.itemcaster.util.CasterLogger;

public class ManaCondition implements IEntityCondition, ISkillMetaComparisonCondition {
	private final PlaceholderString amount;
	public ManaCondition(final MythicLineConfig config) {
		this.amount = config.getPlaceholderString(new String[]{ "amount", "a" }, ">1.0");
	}

	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean check(AbstractEntity e) {
		Entity entity = e.getBukkitEntity();
		if (entity instanceof Player player) {
			if (Storage.mana_engine_type == ManaType.AURA_SKILLS) {
				SkillsUser user = AuraSkillsApi.get().getUser(player.getUniqueId());
				return new RangedDouble(amount.get(e)).equals(user.getMana());
			} else CasterLogger.send(player, "<red>You dont have plugin that has Mana Engine.");
		}
		return false;
	}

	@Override
	public boolean check(SkillMetadata meta, AbstractEntity target) {
		return this.check(target);
	}
}