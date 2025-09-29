package io.phanisment.itemcaster.util;

import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

@SuppressWarnings("deprecation")
public final class ItemAbilityUtil {
	private final ItemStack item;
	private NBTItem nbt_item;
	
	private ItemAbilityUtil(ItemStack item) {
		this.item = item;
		this.nbt_item = new NBTItem(item);
	}
	
	public static ItemAbilityUtil of(ItemStack item) {
		if (!ItemUtil.validateItem(item)) return null;
		return new ItemAbilityUtil(item);
	}
	
	public Optional<ItemStack> addAbility(AbilityData data) {
		NBTCompoundList list = nbt_item.getOrCreateCompound("ItemCaster").getCompoundList("abilities");
		NBTCompound nbt = list.addCompound();
		data.setNBT(nbt);
		
		return Optional.of(nbt_item.getItem());
	}
	
	public Optional<ItemStack> editAbility(int index, Consumer<NBTCompound> callback) {
		NBTCompoundList list = nbt_item.getOrCreateCompound("ItemCaster").getCompoundList("abilities");
		NBTCompound ability = list.get(index);
		callback.accept(ability);
		return Optional.of(nbt_item.getItem());
	}
	
	public Optional<ItemStack> editSkillAbility(int index, String value) {
		return this.editAbility(index, nbt -> nbt.setString("skill", value));
	}
	
	public Optional<ItemStack> editActivatorAbility(int index, String value) {
		return this.editAbility(index, nbt -> nbt.setString("activator", value));
	}
	
	public Optional<ItemStack> editPowerAbility(int index, float value) {
		return this.editAbility(index, nbt -> nbt.setFloat("power", value));
	}
	
	public Optional<ItemStack> editCooldownAbility(int index, double value) {
		return this.editAbility(index, nbt -> nbt.setDouble("cooldown", value));
	}
	
	public Optional<ItemStack> editIntervalAbility(int index, int	value) {
		return this.editAbility(index, nbt -> nbt.setInteger("interval", value));
	}
	
	public Optional<ItemStack> editSneakingAbility(int index, boolean value) {
		return this.editAbility(index, nbt -> nbt.setBoolean("sneaking", value));
	}
	
	public Optional<ItemStack> editSignalAbility(int index, String value) {
		return this.editAbility(index, nbt -> nbt.setString("signal", value));
	}
	
	public Optional<ItemStack> setAbilities(List<AbilityData> data_list) {
		NBTCompoundList nbt = nbt_item.getOrCreateCompound("ItemCaster").getCompoundList("abilities");
		nbt.clear();
		
		for (AbilityData data : data_list) {
			NBTCompound ability = nbt.addCompound();
			data.setNBT(ability);
		}
		return Optional.of(nbt_item.getItem());
	}
	
	public Optional<ItemStack> setAbility(int index, AbilityData data) {
		NBTCompoundList list = nbt_item.getOrCreateCompound("ItemCaster").getCompoundList("abilities");
		NBTCompound nbt = list.get(index);
		nbt.clearNBT();
		data.setNBT(nbt);
		
		return Optional.of(nbt_item.getItem());
	}
	
	public Optional<ItemStack> removeAbility(int index) {
		NBTCompoundList list = nbt_item.getOrCreateCompound("ItemCaster").getCompoundList("abilities");
		list.remove(index);
		return Optional.of(nbt_item.getItem());
	}
	
	public Optional<ItemStack> clearAbilities() {
		NBTCompound nbt = nbt_item.getOrCreateCompound("ItemCaster");
		nbt.removeKey("abilities");
		
		return Optional.of(nbt_item.getItem());
	}
	
	public List<AbilityData> getAbilities() {
		List<AbilityData> list = new ArrayList<>();
		NBTCompoundList abilities = nbt_item.getCompound("ItemCaster").getCompoundList("abilities");
		if (abilities == null) return null;
		for (var comp : abilities) {
			list.add(AbilityData.fromNBT(comp));
		}
		return list;
	}
	
	public AbilityData getAbility(int index) {
		NBTCompoundList abilities = nbt_item.getCompound("ItemCaster").getCompoundList("abilities");
		if (abilities == null) return null;
		return AbilityData.fromNBT(abilities.get(index));
	}
	
	public static class AbilityData {
		private String skill;
		private String activator;
		private Float power;
		private Double cooldown;
		private Integer interval;
		private Boolean sneaking;
		private String signal;
		private Map<String, Object> variables = new HashMap<>();

		public AbilityData(String skill, String activator) {
			this.skill = skill;
			this.activator = activator;
		}

		public void setSkill(String value) {
			this.skill = value;
		}

		public void setActivator(String value) {
			this.activator = value;
		}

		public void setPower(float value) {
			this.power = value;
		}

		public void setCooldown(double value) {
			this.cooldown = value;
		}

		public void setInterval(int value) {
			this.interval = value;
		}

		public void setSneaking(boolean value) {
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
		
		public void setNBT(NBTCompound compound) {
			compound.setString("skill", skill);
			compound.setString("activator", activator);
			
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
							variable_compound.setFloat(key, ((Number)value).floatValue());
						} else {
							variable_compound.setInteger(key, ((Number)value).intValue());
						}
					} else if (value instanceof Boolean) {
						variable_compound.setBoolean(key, (Boolean)value);
					} else {
						variable_compound.setString(key, String.valueOf(value));
					}
				}
			}
		}
		
		public static AbilityData fromNBT(ReadWriteNBT compound) {
			return fromNBT(compound);
		}
		
		public static AbilityData fromNBT(NBTCompound compound) {
			String skill = compound.getString("skill");
			String activator = compound.getString("activator");
			AbilityData data = new AbilityData(skill, activator);
			
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
	}
}