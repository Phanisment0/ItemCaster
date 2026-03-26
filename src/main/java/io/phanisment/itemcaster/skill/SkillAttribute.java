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
	public static final String INTERVAL = "interval";
	public static final String SNEAKING = "sneaking";
	public static final String SIGNAL = "signal";
	public static final String VARIABLES = "variables";
	public static final String CANCEL_EVENT = "cancel_event";

	private Map<String, Object> data = new HashMap<>();

	public String skill;
	public String activator;
	public Float power;
	public Double cooldown;
	public Integer interval;
	public Boolean sneaking;
	public String signal;
	public Map<String, Object> variables = new HashMap<>();
	public Boolean cancel_event;

	public SkillAttribute() {
	}

	public SkillAttribute(Map<String, Object> data) {
		var map = new MapSafe(data);
		this.skill = map.getString(SKILL);
		this.activator = map.getString(ACTIVATOR);
		this.power = map.getFloat(POWER);
		this.cooldown = map.getDouble(COOLDOWN);
		this.interval = map.getInteger(INTERVAL);
		this.sneaking = map.getBoolean(SNEAKING);
		this.signal = map.getString(SIGNAL);
		this.variables = map.getMap(VARIABLES);
		this.cancel_event = map.getBoolean(CANCEL_EVENT);

		this.data = data;
	}

	public SkillAttribute(ReadableNBT data) {
		this.skill = data.getString(SKILL);
		this.activator = data.getString(ACTIVATOR);
		this.power = data.getFloat(POWER);
		this.cooldown = data.getDouble(COOLDOWN);
		this.interval = data.getInteger(INTERVAL);
		this.sneaking = data.getBoolean(SNEAKING);
		this.signal = data.getString(SIGNAL);
		this.cancel_event = data.getBoolean(CANCEL_EVENT);
		
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

		applyToData();
	}

	public boolean contains(String attribute_name) {
		return data.containsKey(attribute_name);
	}

	private void applyToData() {
		if (skill != null) data.put(SKILL, skill);
		if (activator != null) data.put(ACTIVATOR, activator);
		if (power != null) data.put(POWER, power);
		if (cooldown != null) data.put(COOLDOWN, cooldown);
		if (interval != null) data.put(INTERVAL, interval);
		if (sneaking != null) data.put(SNEAKING, sneaking);
		if (signal != null) data.put(SIGNAL, signal);
		if (!variables.isEmpty()) data.put(VARIABLES, variables);
		if (cancel_event != null) data.put(CANCEL_EVENT, cancel_event);
	}

	public void setNBT(ReadWriteNBT compound) {
		if (skill != null) compound.setString(SKILL, skill);
		if (activator != null) compound.setString(ACTIVATOR, activator);

		if (power != null) compound.setFloat(POWER, power);
		if (cooldown != null) compound.setDouble(COOLDOWN, cooldown);
		if (interval != null) compound.setInteger(INTERVAL, interval);
		if (sneaking != null) compound.setBoolean(SNEAKING, sneaking);
		if (signal != null) compound.setString(SIGNAL, signal);
		if (cancel_event != null) compound.setBoolean(CANCEL_EVENT, cancel_event);

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