package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import io.lumine.mythic.api.config.MythicLineConfig;

import java.util.Optional;

public class ResetEnchantmentItemMechanic extends ItemMechanic {
	public ResetEnchantmentItemMechanic(MythicLineConfig config) {
		super(config);
	}
	
	@Override
	public Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		if (item == null) Optional.empty();
		
		item.removeEnchantments();
		return Optional.of(item);
	}
}