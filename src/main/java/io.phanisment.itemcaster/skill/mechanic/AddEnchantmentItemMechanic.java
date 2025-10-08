package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.Material;
import io.lumine.mythic.api.config.MythicLineConfig;

import java.util.Optional;

public class AddEnchantmentItemMechanic extends ItemMechanic {
	private Enchantment enchantment;
	private final int level;
	private final boolean unsafe;
	
	public AddEnchantmentItemMechanic(MythicLineConfig config) {
		super(config);
		String raw_ec = config.getString(new String[]{"enchantment", "enchant", "e"}, "FIRE_ASPECT");
		try {
			this.enchantment = Enchantment.getByName(raw_ec);
		} catch (EnumConstantNotPresentException e) {
			this.enchantment = Enchantment.FIRE_ASPECT;
		}
		this.level = config.getInteger(new String[]{"level", "l"}, 1);
		this.unsafe = config.getBoolean(new String[]{"unsafe", "us"}, true);
	}
	
	@Override
	protected Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		if (item == null) Optional.empty();
		if (unsafe) {
			item.addUnsafeEnchantment(enchantment, level);
		} else {
			item.addEnchantment(enchantment, level);
		}
		return Optional.of(item);
	}
}