package io.phanisment.itemcaster.skill;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;

import io.phanisment.itemcaster.util.MapSafe;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

public class SkillAttribute {
	private static final String SKILL = "skill";
	private static final String ACTIVATOR = "aactivator";
	private static final String POWER = "power";
	private static final String COOLDOWN = "cooldown";
	private static final String INTERVAL = "interval";
	private static final String SNEAKING = "sneaking";
	private static final String SIGNAL = "signal";
	private static final String VARIABLES = "variables";

	private String skill;
	private String activator;
	private Float power;
	private Double cooldown;
	private Integer interval;
	private Boolean sneaking;
	private String signal;
	private Map<String, Object> variables = new HashMap<>();

	public SkillAttribute() {
	}

	public SkillAttribute(String skill, String activator) {
		this.skill = skill;
		this.activator = activator;
	}

	public void setSkill(String value) {
		this.skill = value;
	}

	public void setActivator(String value) {
		this.activator = value;
	}

	public void setPower(Float value) {
		this.power = value;
	}

	public void setCooldown(Double value) {
		this.cooldown = value;
	}

	public void setInterval(Integer value) {
		this.interval = value;
	}

	public void setSneaking(Boolean value) {
		this.sneaking = value;
	}

	public void setSignal(String value) {
		this.signal = value;
	}

	public void setVariables(Map<String, Object> value) {
		this.variables = value;
	}

	public void putVariable(String key, Object value) {
		this.variables.put(key, value);
	}

	public String getSkill() {
		return this.skill;
	}

	public String getActivator() {
		return this.activator;
	}

	public Float getPower() {
		return this.power;
	}

	public Double getCooldown() {
		return this.cooldown;
	}

	public Integer getInterval() {
		return this.interval;
	}

	public Boolean getSneaking() {
		if (sneaking == null) return false;
		return this.sneaking;
	}

	public String getSignal() {
		return this.signal;
	}

	public Map<String, Object> getVariables() {
		return this.variables;
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		if (this.skill != null) map.put(SKILL, this.skill);
		if (this.activator != null) map.put(ACTIVATOR, this.activator);
		if (this.power != null) map.put(POWER, this.power);
		if (this.cooldown != null) map.put(COOLDOWN, this.cooldown);
		if (this.interval != null) map.put(INTERVAL, this.interval);
		if (this.sneaking != null) map.put(SNEAKING, this.sneaking);
		if (this.signal != null) map.put(SIGNAL, this.signal);
		if (!this.variables.isEmpty()) map.put(VARIABLES, this.variables);
		return map;
	}

	public static SkillAttribute fromMap(Map<String, Object> map) {
		var data = new SkillAttribute();
		var safe = new MapSafe(map);
		if (safe.contains(SKILL)) data.skill = safe.getString(SKILL);
		if (safe.contains(ACTIVATOR)) data.activator = safe.getString(ACTIVATOR);
		if (safe.contains(POWER)) data.power = safe.getFloat(POWER);
		if (safe.contains(COOLDOWN)) data.cooldown = safe.getDouble(COOLDOWN);
		if (safe.contains(INTERVAL)) data.interval = safe.getInteger(INTERVAL);
		if (safe.contains(SNEAKING)) data.sneaking = safe.getBoolean(SNEAKING);
		if (safe.contains(SIGNAL)) data.signal = safe.getString(SIGNAL);
		if (safe.contains(VARIABLES)) data.variables = safe.getMap(VARIABLES);
		return data;
	}

	public void setNBT(NBTCompound compound) {
		if (skill != null) compound.setString(SKILL, skill);
		if (activator != null) compound.setString(ACTIVATOR, activator);

		if (power != null) compound.setFloat(POWER, power);
		if (cooldown != null) compound.setDouble(COOLDOWN, cooldown);
		if (interval != null) compound.setInteger(INTERVAL, interval);
		if (sneaking != null) compound.setBoolean(SNEAKING, sneaking);
		if (signal != null) compound.setString(SIGNAL, signal);

		if (!variables.isEmpty()) {
			NBTCompound variable_compound = compound.getOrCreateCompound(VARIABLES);
			for (Map.Entry<String, Object> entry : variables.entrySet()) {
				Object value = entry.getValue();
				String key = entry.getKey();

				if (value instanceof Number) {
					if (value instanceof Float || value instanceof Double) variable_compound.setFloat(key, ((Number) value).floatValue());
					else variable_compound.setInteger(key, ((Number) value).intValue());
				} 
				else if (value instanceof Boolean) variable_compound.setBoolean(key, (Boolean) value);
				else variable_compound.setString(key, String.valueOf(value));
			}
		}
	}

	public static SkillAttribute fromNBT(ReadWriteNBT compound) {
		return fromNBT(compound);
	}

	public static SkillAttribute fromNBT(NBTCompound compound) {
		var data = new SkillAttribute();
		if (compound.hasTag(SKILL)) data.setSkill(compound.getString(SKILL));
		if (compound.hasTag(ACTIVATOR)) data.setActivator(compound.getString(ACTIVATOR));
		if (compound.hasTag(POWER)) data.setPower(compound.getFloat(POWER));
		if (compound.hasTag(COOLDOWN)) data.setCooldown(compound.getDouble(COOLDOWN));
		if (compound.hasTag(INTERVAL)) data.setInterval(compound.getInteger(INTERVAL));
		if (compound.hasTag(SNEAKING)) data.setSneaking(compound.getBoolean(SNEAKING));
		if (compound.hasTag(SIGNAL)) data.setSignal(compound.getString(SIGNAL));

		if (compound.hasTag(VARIABLES)) {
			NBTCompound vars = compound.getCompound(VARIABLES);
			for (String key : vars.getKeys()) {
				switch (vars.getType(key)) {
					case NBTTagFloat -> data.putVariable(key, vars.getFloat(key));
					case NBTTagInt -> data.putVariable(key, vars.getInteger(key));
					case NBTTagString -> data.putVariable(key, vars.getString(key));
					case NBTTagByte -> data.putVariable(key, vars.getBoolean(key));
					default -> data.putVariable(key, vars.getString(key));
				}
			}
		}
		return data;
	}

	public List<String> toStringList() {
		List<String> list = new ArrayList<>();
		if (skill != null) list.add("skill: " + skill);
		if (activator != null) list.add("activator: " + activator);
		if (power != null) list.add("power: " + String.valueOf(power));
		if (cooldown != null) list.add("cooldown: " + String.valueOf(cooldown));
		if (interval != null) list.add("interval: " + String.valueOf(interval));
		if (sneaking != null) list.add("sneaking: " + String.valueOf(sneaking));
		if (signal != null) list.add("signal: " + signal);
		if (!variables.isEmpty()) list.add("variables: ...");
		return list;
	}
}