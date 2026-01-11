package io.phanisment.itemcaster.skill;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;

import io.phanisment.itemcaster.util.MapSafe;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

/**
 * I need to refactor this shit later...
 */
public class SkillAttribute {
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
		if (this.skill != null) map.put("skill", this.skill);
		if (this.activator != null) map.put("activator", this.activator);
		if (this.power != null) map.put("power", this.power);
		if (this.cooldown != null) map.put("cooldown", this.cooldown);
		if (this.interval != null) map.put("interval", this.interval);
		if (this.sneaking != null) map.put("sneaking", this.sneaking);
		if (this.signal != null) map.put("signal", this.signal);
		if (!this.variables.isEmpty()) map.put("variables", this.variables);
		return map;
	}

	public static SkillAttribute fromMap(Map<String, Object> map) {
		var data = new SkillAttribute();
		var safe = new MapSafe(map);
		if (safe.contains("skill")) data.skill = safe.getString("skill");
		if (safe.contains("activator")) data.activator = safe.getString("activator");
		if (safe.contains("power")) data.power = safe.getFloat("power");
		if (safe.contains("cooldown")) data.cooldown = safe.getDouble("cooldown");
		if (safe.contains("interval")) data.interval = safe.getInteger("interval");
		if (safe.contains("signal")) data.signal = safe.getString("signal");
		if (safe.contains("variables")) data.variables = safe.getMap("variables");
		return data;
	}

	public void setNBT(NBTCompound compound) {
		if (skill != null) compound.setString("skill", skill);
		if (activator != null) compound.setString("activator", activator);

		if (power != null) compound.setFloat("power", power);
		if (cooldown != null) compound.setDouble("cooldown", cooldown);
		if (interval != null) compound.setInteger("interval", interval);
		if (sneaking != null) compound.setBoolean("sneaking", sneaking);
		if (signal != null) compound.setString("signal", signal);

		if (!variables.isEmpty()) {
			NBTCompound variable_compound = compound.getOrCreateCompound("variables");
			for (Map.Entry<String, Object> entry : variables.entrySet()) {
				Object value = entry.getValue();
				String key = entry.getKey();

				if (value instanceof Number) {
					if (value instanceof Float || value instanceof Double) {
						variable_compound.setFloat(key, ((Number) value).floatValue());
					} else {
						variable_compound.setInteger(key, ((Number) value).intValue());
					}
				} else if (value instanceof Boolean) {
					variable_compound.setBoolean(key, (Boolean) value);
				} else {
					variable_compound.setString(key, String.valueOf(value));
				}
			}
		}
	}

	public static SkillAttribute fromNBT(ReadWriteNBT compound) {
		return fromNBT(compound);
	}

	public static SkillAttribute fromNBT(NBTCompound compound) {
		var data = new SkillAttribute();
		if (compound.hasTag("skill")) data.setSkill(compound.getString("skill"));
		if (compound.hasTag("activator")) data.setActivator(compound.getString("activator"));
		if (compound.hasTag("power")) data.setPower(compound.getFloat("power"));
		if (compound.hasTag("cooldown")) data.setCooldown(compound.getDouble("cooldown"));
		if (compound.hasTag("interval")) data.setInterval(compound.getInteger("interval"));
		if (compound.hasTag("sneaking")) data.setSneaking(compound.getBoolean("sneaking"));
		if (compound.hasTag("signal")) data.setSignal(compound.getString("signal"));

		if (compound.hasTag("variables")) {
			NBTCompound vars = compound.getCompound("variables");
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