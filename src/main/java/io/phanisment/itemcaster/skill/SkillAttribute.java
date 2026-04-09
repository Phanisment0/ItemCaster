package io.phanisment.itemcaster.skill;

import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadableNBT;
import io.phanisment.itemcaster.util.MapSafe;

import java.util.Map;
import java.util.HashMap;

public class SkillAttribute {
	public static final String SKILL = "skill";
	public static final String ACTIVATOR = "activator";
	public static final String POWER = "power";
	public static final String COOLDOWN = "cooldown";
	public static final String SNEAKING = "sneaking";
	public static final String SIGNAL = "signal";
	public static final String VARIABLES = "variables";
	public static final String CANCEL_EVENT = "cancel_event";
	public static final String SHOW_COOLDOWN = "show_cooldown";

	public String skill;
	public String activator;
	public Float power;
	public Double cooldown;
	public Boolean sneaking;
	public String signal;
	public Map<String, Object> variables = new HashMap<>();
	public Boolean cancel_event;
	public Boolean show_cooldown;

	public SkillAttribute() {
	}

	public SkillAttribute(Map<String, Object> data) {
		var map = new MapSafe(data);
		if (map.contains(SKILL)) this.skill = map.getString(SKILL);
		if (map.contains(ACTIVATOR)) this.activator = map.getString(ACTIVATOR);
		if (map.contains(POWER)) this.power = map.getFloat(POWER);
		if (map.contains(COOLDOWN)) this.cooldown = map.getDouble(COOLDOWN);
		if (map.contains(SNEAKING)) this.sneaking = map.getBoolean(SNEAKING);
		if (map.contains(SIGNAL)) this.signal = map.getString(SIGNAL);
		if (map.contains(VARIABLES)) this.variables = map.getMap(VARIABLES);
		if (map.contains(CANCEL_EVENT)) this.cancel_event = map.getBoolean(CANCEL_EVENT);
		if (map.contains(SHOW_COOLDOWN)) this.show_cooldown = map.getBoolean(SHOW_COOLDOWN);
	}

	public SkillAttribute(ReadableNBT data) {
		if (data.hasTag(SKILL)) this.skill = data.getString(SKILL);
		if (data.hasTag(ACTIVATOR)) this.activator = data.getString(ACTIVATOR);
		if (data.hasTag(POWER)) this.power = data.getFloat(POWER);
		if (data.hasTag(COOLDOWN)) this.cooldown = data.getDouble(COOLDOWN);
		if (data.hasTag(SNEAKING)) this.sneaking = data.getBoolean(SNEAKING);
		if (data.hasTag(SIGNAL)) this.signal = data.getString(SIGNAL);
		if (data.hasTag(CANCEL_EVENT)) this.cancel_event = data.getBoolean(CANCEL_EVENT);
		if (data.hasTag(SHOW_COOLDOWN)) this.show_cooldown = data.getBoolean(SHOW_COOLDOWN);
		
		if (data.hasTag(VARIABLES)) {
			ReadableNBT variables_nbt = data.getCompound(VARIABLES);
			for (String key : variables_nbt.getKeys()) {
				switch (variables_nbt.getType(key)) {
					case NBTTagFloat -> variables.put(key, variables_nbt.getFloat(key));
					case NBTTagInt -> variables.put(key, variables_nbt.getInteger(key));
					case NBTTagString -> variables.put(key, variables_nbt.getString(key));
					case NBTTagByte -> variables.put(key, variables_nbt.getByte(key));
					default -> variables.put(key, variables_nbt.getString(key));
				}
			}
		}
	}

	public Map<String, Object> toMap() {
		Map<String, Object> map = new HashMap<>();
		if (this.skill != null) map.put(SKILL, this.skill);
		if (this.activator != null) map.put(ACTIVATOR, this.activator);
		if (this.power != null) map.put(POWER, this.power);
		if (this.cooldown != null) map.put(COOLDOWN, this.cooldown);
		if (this.sneaking != null) map.put(SNEAKING, this.sneaking);
		if (this.signal != null) map.put(SIGNAL, this.signal);
		if (!this.variables.isEmpty()) map.put(VARIABLES, this.variables);
		if (this.show_cooldown != null) map.put(SHOW_COOLDOWN, this.show_cooldown);
		return map;
	}

	public void setNBT(ReadWriteNBT compound) {
		if (skill != null) compound.setString(SKILL, skill);
		if (activator != null) compound.setString(ACTIVATOR, activator);
		if (power != null) compound.setFloat(POWER, power);
		if (cooldown != null) compound.setDouble(COOLDOWN, cooldown);
		if (sneaking != null) compound.setBoolean(SNEAKING, sneaking);
		if (signal != null) compound.setString(SIGNAL, signal);
		if (cancel_event != null) compound.setBoolean(CANCEL_EVENT, cancel_event);
		if (show_cooldown != null) compound.setBoolean(SHOW_COOLDOWN, show_cooldown);

		if (!variables.isEmpty()) {
			ReadWriteNBT variable_compound = compound.getOrCreateCompound(VARIABLES);
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
}