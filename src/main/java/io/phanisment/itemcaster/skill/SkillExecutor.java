package io.phanisment.itemcaster.skill;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.parser.ProgressBarParse;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.MythicMobsUtil;

import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.skills.SkillMetadataImpl;
import io.lumine.mythic.core.skills.SkillTriggers;
import io.lumine.mythic.core.skills.MetaSkill;
import io.lumine.mythic.core.skills.variables.VariableRegistry;

import java.util.HashMap;
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
		return this;
	}

	public SkillExecutor setCooldown(Double cooldown) {
		this.cooldown = cooldown;
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
		var meta = new SkillMetadataImpl(SkillTriggers.API, caster, BukkitAdapter.adapt(player));
		if (power != null) meta.setPower(power);
		if (variables != null) {
			VariableRegistry skill_var = meta.getVariables();
			for (var map : variables.entrySet()) {
				String key = map.getKey();
				Object value = map.getValue();
				if (value instanceof Float value_float) skill_var.putFloat(key, value_float);
				else if (value instanceof Integer value_integer) skill_var.putInt(key, value_integer);
				else if (value instanceof Double value_double) skill_var.putDouble(key, value_double);
				else skill_var.putString(key, (String)value);
			}
		}

		MetaSkill meta_skill = (MetaSkill)skill;
		if (cooldown == null) cooldown = 0d;
		
		if (skill.onCooldown(caster) && show_cooldown) {
			// Don't delete this, beacuse this needed for older version of Mythicmobs,
			// in older version return value of method getCooldown is `float` and  in
			// newer version is double.
			double cd = (double)meta_skill.getCooldown(caster);
			var progress_bar = new ProgressBarParse(cd, cooldown);
			player.sendActionBar(CasterLogger.MM.deserialize(skill.getInternalName() + " " + progress_bar.parse() + " " + progress_bar.formatedTime()));
		}

		// If skill is useable/can be casted, like pass the condition and cooldown
		// then cast skill and set the cooldown if not in cooldown.
		if (!skill.isUsable(meta, SkillTriggers.API)) return;
		skill.execute(meta);
		if (cooldown > 0) meta_skill.setCooldown(caster, cooldown);
	}
}