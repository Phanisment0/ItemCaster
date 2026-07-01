package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.phanisment.itemcaster.skill.template.ItemMechanic;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.util.Optional;

public class ClearEnchantmentItemMechanic extends ItemMechanic {
	public ClearEnchantmentItemMechanic(MythicLineConfig mlc) {
		super(mlc);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.removeEnchantments();
		return Optional.empty();
	}
}