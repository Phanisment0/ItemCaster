package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.phanisment.itemcaster.skill.template.ItemMechanic;

import java.util.Optional;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

public class RemoveEnchantmentItemMechanic extends ItemMechanic {
	private Enchantment enchantment;

	@SuppressWarnings("deprecation")
	public RemoveEnchantmentItemMechanic(MythicLineConfig mlc) {
		super(mlc);
		String string_enc = mlc.getString(new String[] { "enchantment", "en" }, "UNBREAKING").toUpperCase();
		this.enchantment = Enchantment.getByName(string_enc);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.removeEnchantment(enchantment);
		return Optional.empty();
	}
}