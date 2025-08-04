package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.LivingEntity;
import org.bukkit.Material;
import io.lumine.mythic.api.config.MythicLineConfig;

import java.util.Optional;

public class SetTypeItemMechanic extends ItemMechanic {
	private Material type;
	
	public SetTypeItemMechanic(MythicLineConfig config) {
		super(config);
		String raw_type = config.getString(new String[]{"material", "type", "m", "t"}, "STONE");
		try {
			this.type = Material.valueOf(raw_type.toUpperCase());
		} catch (EnumConstantNotPresentException e) {
			this.type = Material.STONE;
		}
	}
	
	@Override
	protected Optional<ItemStack> resolve(LivingEntity target, ItemStack item) {
		if (item == null) Optional.empty();
		item.setType(type);
		return Optional.of(item);
	}
}