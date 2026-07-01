package io.phanisment.itemcaster.skill.placeholder;

import java.util.UUID;

import dev.aurelium.auraskills.api.AuraSkillsApi;
import dev.aurelium.auraskills.api.user.SkillsUser;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.skills.placeholders.PlaceholderExecutor;
import io.lumine.mythic.core.skills.placeholders.PlaceholderMeta;
import io.lumine.mythic.core.skills.placeholders.all.EntityNumericPlaceholder;
import io.lumine.mythic.core.skills.placeholders.types.MetaPlaceholder;
import io.lumine.mythic.core.utils.annotations.MythicPlaceholder;
import io.phanisment.itemcaster.Storage;
import io.phanisment.itemcaster.mana_engine.ManaType;

@MythicPlaceholder(placeholder="caster.mana")
public class CasterManaPlaceholder extends EntityNumericPlaceholder implements MetaPlaceholder {
	public CasterManaPlaceholder(PlaceholderExecutor manager, MythicLineConfig config, String[] args) {
		super(manager, config);
	}

	@Override
	public String apply(PlaceholderMeta meta, String args) {
		if (Storage.mana_engine_type == ManaType.INTERNAL) return "";
		UUID uuid = meta.getTrigger().getUniqueId();
		SkillsUser user = AuraSkillsApi.get().getUser(uuid);
		return String.valueOf(user.getMana());
	}
}