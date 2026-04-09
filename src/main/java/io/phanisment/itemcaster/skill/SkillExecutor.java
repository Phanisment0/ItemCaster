package io.phanisment.itemcaster.skill;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.core.utils.MythicUtil;
import io.phanisment.itemcaster.parser.ProgressBarParse;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.MythicMobsUtil;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.core.skills.SkillMetadataImpl;
import io.lumine.mythic.core.skills.SkillTriggers;
import io.lumine.mythic.core.skills.AbstractSkill;
import io.lumine.mythic.core.skills.MetaSkill;
import io.lumine.mythic.core.skills.variables.VariableRegistry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillExecutor {
	private final Player player;
	private final SkillCaster caster;
	private final Skill skill;

	private Float power;
	private Double cooldown;
	private boolean show_cooldown = false;
	private Map<String, Object> variables = new HashMap<>();

	public SkillExecutor(Skill skill, Player player) {
		this.player = player;
		this.caster = MythicMobsUtil.toCaster(player);
		this.skill = skill;
	}

	public SkillExecutor setPower(Float power) {
		this.power = power;
		if (power == null) power = 0f;
		return this;
	}

	public SkillExecutor setCooldown(Double cooldown) {
		this.cooldown = cooldown;
		if (cooldown == null) cooldown = 0d;
		return this;
	}

	public SkillExecutor setShowCooldown(boolean show) {
		this.show_cooldown = show;
		return this;
	}

	public SkillExecutor setVariables(Map<String, Object> variables) {
		this.variables = variables;
		return this;
	}

	public SkillExecutor setAttribute(SkillAttribute attributes) {
		this.power = attributes.power;
		this.cooldown = attributes.cooldown;
		this.variables = attributes.variables;
		this.show_cooldown = attributes.show_cooldown;
		return this;
	}

	public void execute() {
		List<AbstractEntity> abstract_entities = new ArrayList<>();
		List<AbstractLocation> abstract_location = new ArrayList<>();

		LivingEntity target = MythicUtil.getTargetedEntity(player);
		if (target != null) {
			abstract_entities.add(BukkitAdapter.adapt(target));
			abstract_location.add(BukkitAdapter.adapt(target.getLocation()));
		}

		if (power == null) power = 0f; // Im worried about null exception so i check 2 times lol
		var meta = new SkillMetadataImpl(SkillTriggers.API, caster, BukkitAdapter.adapt(player), BukkitAdapter.adapt(player.getLocation()), abstract_entities, abstract_location, this.power);

		if (variables != null) {
			VariableRegistry skill_var = meta.getVariables();
			for (var map : variables.entrySet()) {
				String key = map.getKey();
				Object value = map.getValue();
				if (value instanceof Float value_float) skill_var.putFloat(key, value_float);
				else if (value instanceof Double value_double) skill_var.putDouble(key, value_double);
				else if (value instanceof Integer value_integer) skill_var.putInt(key, value_integer);
				else skill_var.putString(key, (String)value);
			}
		}

		float cd = ((MetaSkill)skill).getCooldown(caster);

		if (cooldown == null) cooldown = 0d; // this too ;v
		var progress_bar = new ProgressBarParse(cd, cooldown.floatValue());
		if (skill.onCooldown(caster)) {
			if (show_cooldown) player.sendActionBar(CasterLogger.MM.deserialize(progress_bar.parse() + " " + progress_bar.formatedTime()));
			return;
		}

		if (!skill.isUsable(meta, SkillTriggers.API)) return;

		skill.execute(meta);
		if (cooldown > 0) {
			((MetaSkill)skill).setCooldown(caster, cooldown);
			if (show_cooldown) player.sendActionBar(CasterLogger.MM.deserialize(progress_bar.parse() + " " + progress_bar.formatedTime()));
		}
	}
}