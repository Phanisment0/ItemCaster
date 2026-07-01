package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.phanisment.itemcaster.skill.template.ItemMechanic;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.util.Optional;

public class SetDurabilityItemMechanic extends ItemMechanic {
	private final PlaceholderInt amount;

	public SetDurabilityItemMechanic(MythicLineConfig mlc) {
		super(mlc);
		this.amount = mlc.getPlaceholderInteger(new String[] { "amount", "a" }, 1);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		ItemMeta meta = item.getItemMeta();
		if (meta instanceof Damageable dmg) {
			dmg.setDamage(amount.get(target));
			item.setItemMeta(dmg);
			return Optional.of(item);
		}
		return Optional.empty();
	}
}