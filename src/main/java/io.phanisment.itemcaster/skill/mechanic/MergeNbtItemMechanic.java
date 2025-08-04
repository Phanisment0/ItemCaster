package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.LivingEntity;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;

import io.lumine.mythic.api.config.MythicLineConfig;

import java.util.Optional;

public class MergeNbtItemMechanic extends ItemMechanic {
	private final String raw_nbt;
	
	public MergeNbtItemMechanic(MythicLineConfig config) {
		super(config);
		this.raw_nbt = config.getPlaceholderString(new String[]{"nbt", "n"}, "").get();
	}
	
	@Override
	protected Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		if (item == null && raw_nbt.isEmpty()) return Optional.empty();
		NBTItem nbt = new NBTItem(item);
		try {
			ReadWriteNBT convert = NBT.parseNBT(raw_nbt);
			nbt.mergeCompound(convert);
		} catch (Exception e) {
		}
		return Optional.of(nbt.getItem());
	}
}