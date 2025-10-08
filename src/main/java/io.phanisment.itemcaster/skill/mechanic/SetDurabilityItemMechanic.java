package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import io.lumine.mythic.api.config.MythicLineConfig;

import java.util.Optional;

public class SetDurabilityItemMechanic extends ItemMechanic {
	private final int amount;
	
	public SetDurabilityItemMechanic(MythicLineConfig config) {
		super(config);
		this.amount = config.getInteger(new String[]{"amount", "a"}, 1);
	}
	
	@Override
	protected Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		if (item == null) Optional.empty();
		ItemMeta meta = item.getItemMeta();
		if (meta instanceof Damageable damageable) damageable.setDamage(amount);
		item.setItemMeta(meta);
		return Optional.of(item);
	}
}