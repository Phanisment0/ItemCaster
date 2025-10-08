package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import io.lumine.mythic.api.config.MythicLineConfig;

import java.util.Optional;

public class SetModelDataItemMechanic extends ItemMechanic {
	private final int model;
	
	public SetModelDataItemMechanic(MythicLineConfig config) {
		super(config);
		this.model = config.getInteger(new String[]{"model", "m"}, 1000);
	}
	
	@Override
	protected Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		if (item == null) Optional.empty();
		ItemMeta meta = item.getItemMeta();
		meta.setCustomModelData(model);
		item.setItemMeta(meta);
		return Optional.of(item);
	}
}