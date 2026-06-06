package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.logging.MythicLogger;
import io.phanisment.itemcaster.skill.template.ItemMechanic;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.util.Optional;

public class SetTypeItemMechanic extends ItemMechanic {
	private Material type = Material.STONE;

	public SetTypeItemMechanic(MythicLineConfig mlc) {
		super(mlc);
		String type_string = mlc.getString(new String[] { "material", "m", "type", "t" }, "STONE").toUpperCase();
		try {
			this.type = Material.valueOf(type_string);
		} catch (IllegalArgumentException e) {
			MythicLogger.error("Invalid material: " + type_string);
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.setType(this.type);
		return Optional.of(item);
	}
}