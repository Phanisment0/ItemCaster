package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.entity.LivingEntity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.phanisment.itemcaster.util.ItemUtil;

import java.util.Optional;

public class SetItemMechanic extends ItemMechanic {
	private String replace_with;
	
	public SetItemMechanic(MythicLineConfig config) {
		super(config);
		this.replace_with = config.getString(new String[]{"item", "replace", "i", "r"}, "STONE");
	}
	
	@Override
	public Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		return Optional.of(ItemUtil.getItem(replace_with));
	}
}