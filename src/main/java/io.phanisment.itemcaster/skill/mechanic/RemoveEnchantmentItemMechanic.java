package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Material;
import io.lumine.mythic.api.config.MythicLineConfig;

import java.util.Optional;

public class RemoveEnchantmentItemMechanic extends ItemMechanic {
	private Enchantment enchantment;
	
	public RemoveEnchantmentItemMechanic(MythicLineConfig config) {
		super(config);
		String raw_ec = config.getString(new String[]{"enchantment", "enchant", "e"}, "FIRE_ASPECT");
		try {
			this.enchantment = Enchantment.getByName(raw_ec);
		} catch (EnumConstantNotPresentException e) {
			this.enchantment = Enchantment.FIRE_ASPECT;
		}
	}
	
	@Override
	public Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		if (item == null) Optional.empty();
		
		item.removeEnchantment(enchantment);
		return Optional.of(item);
	}
}