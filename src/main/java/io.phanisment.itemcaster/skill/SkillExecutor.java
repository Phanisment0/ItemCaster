package io.phanisment.itemcaster.skill;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.core.utils.MythicUtil;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.core.skills.SkillMetadataImpl;
import io.lumine.mythic.core.skills.SkillTriggers;
import io.lumine.mythic.core.skills.MetaSkill;

import de.tr7zw.nbtapi.iface.ReadableNBT;

import java.util.ArrayList;
import java.util.List;

public class SkillExecutor {
	private final Player player;
	private final SkillCaster caster;
	private final MetaSkill skill;
	
	private float power;
	private double cooldown;
	private ReadableNBT variables;
	
	public SkillExecutor(MetaSkill skill, Player player) {
		this.player = player;
		this.caster = SkillActivator.toCaster(player);
		this.skill = skill;
	}
	
	public SkillExecutor setPower(float power) {
		this.power = power;
		return this;
	}
	
	public SkillExecutor setCooldown(double cooldown) {
		this.cooldown = cooldown;
		return this;
	}
	
	public SkillExecutor setVariables(ReadableNBT variables) {
		this.variables = variables;
		return this;
	}
	
	public void execute() {
		List<AbstractEntity> aEntity = new ArrayList<>();
		List<AbstractLocation> aLocation = new ArrayList<>();
		
		LivingEntity target = MythicUtil.getTargetedEntity(player);
		if (target != null) {
			aEntity.add(BukkitAdapter.adapt(target));
			aLocation.add(BukkitAdapter.adapt(target.getLocation()));
		}
		
		SkillMetadataImpl meta = new SkillMetadataImpl(SkillTriggers.API, caster, BukkitAdapter.adapt(player), BukkitAdapter.adapt(player.getLocation()), aEntity, aLocation, this.power);
		
		if (variables != null) {
			for (String key : variables.getKeys()) {
				switch(variables.getType(key)) {
					case NBTTagFloat:
						meta.getVariables().putFloat(key, variables.getFloat(key));
						break;
					case NBTTagInt:
						meta.getVariables().putInt(key, variables.getInteger(key));
						break;
					case NBTTagString:
						meta.getVariables().putString(key, variables.getString(key));
						break;
					default:
						meta.getVariables().putFloat(key, variables.getFloat(key));
						break;
				}
			}
		}
		if (skill.isUsable(meta, SkillTriggers.API)) {
			skill.execute(meta);
			skill.setCooldown(caster, cooldown);
		}
	}
}