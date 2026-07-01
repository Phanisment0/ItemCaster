package io.phanisment.itemcaster.hand;

import java.util.List;

import org.bukkit.entity.Player;

import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.SkillCondition;
import io.lumine.mythic.core.skills.SkillMetadataImpl;
import io.lumine.mythic.core.skills.SkillTriggers;

import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.skill.IActivator;
import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.util.Identifier;
import io.phanisment.itemcaster.util.MythicMobsUtil;

public class HandActivator {
	private final Player player;
	private final IActivator activator;
	private Identifier ability_id = null;
	private String signal = null;
	private ProfileData data;

	public HandActivator(Player player, IActivator activator) {
		this.player = player;
		this.activator = activator;
		this.data = ProfileManager.get(player).getData();
		ability_id = data.hand_ability;
	}

	public HandActivator setSignal(String signal) {
		this.signal = signal;
		return this;
	}

	public void execute() {
		if (!data.hand_toggle) return;
		if (ability_id == null) return;
		HandAbilityAttribute abilities = HandCaster.getAbility(ability_id);
		if (abilities == null) return;
		
		if (!isUsable(abilities, getMeta())) return;

		var exc = new SkillActivator(player, activator);
		if (signal != null) exc.setSignal(signal);
		exc.setAttributes(abilities.getAttributes()).execute();
		
	}

	private SkillMetadata getMeta() {
		SkillCaster caster = MythicMobsUtil.toCaster(player);
		return new SkillMetadataImpl(SkillTriggers.API, caster, BukkitAdapter.adapt(player));
	}

	private boolean isUsable(HandAbilityAttribute abilites, SkillMetadata meta) {
		List<SkillCondition> conditions = abilites.getConditions();
		if (conditions.isEmpty()) return true;
		for (SkillCondition sc : conditions) if (!sc.evaluateCaster(meta)) return false;
		return true;
	}
}
