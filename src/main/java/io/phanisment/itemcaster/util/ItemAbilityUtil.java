package io.phanisment.itemcaster.util;

import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.Nullable;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;

import io.phanisment.itemcaster.skill.SkillAttribute;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Consumer;

/**
 * Util for edit item Ability.
 */
@SuppressWarnings("deprecation")
public final class ItemAbilityUtil {
	@Nullable
	private ItemStack item;
	private NBTItem nbt_item;
	
	private ItemAbilityUtil(ItemStack item) {
		this.item = item;
		this.nbt_item = new NBTItem(item);
	}
	
	/**
	 * Create instance of ItemAbilityUtil.
	 * 
	 * @param item Item that want to edit.
	 * @return     Instance of ItemAbilityUtil. if item is not valid, will return null.
	 */
	public static ItemAbilityUtil of(ItemStack item) {
		if (!ItemUtil.validateItem(item)) return null;
		return new ItemAbilityUtil(item);
	}
	
	public Optional<ItemStack> addAbility(SkillAttribute data) {
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
	
	public Optional<ItemStack> setAbilities(List<SkillAttribute> data_list) {
		NBTCompoundList nbt = nbt_item.getOrCreateCompound("ItemCaster").getCompoundList("abilities");
		nbt.clear();
		
		for (SkillAttribute data : data_list) {
			NBTCompound ability = nbt.addCompound();
			data.setNBT(ability);
		}
		return Optional.of(nbt_item.getItem());
	}
	
	public Optional<ItemStack> setAbility(int index, SkillAttribute data) {
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
	
	public List<SkillAttribute> getAbilities() {
		List<SkillAttribute> list = new ArrayList<>();
		NBTCompoundList abilities = nbt_item.getCompound("ItemCaster").getCompoundList("abilities");
		if (abilities == null) return null;
		for (var comp : abilities) {
			list.add(SkillAttribute.fromNBT(comp));
		}
		return list;
	}
	
	public SkillAttribute getAbility(int index) {
		NBTCompoundList abilities = nbt_item.getCompound("ItemCaster").getCompoundList("abilities");
		if (abilities == null) return null;
		return SkillAttribute.fromNBT(abilities.get(index));
	}
}