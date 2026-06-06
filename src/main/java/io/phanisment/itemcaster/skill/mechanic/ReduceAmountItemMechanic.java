package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.phanisment.itemcaster.skill.template.ItemMechanic;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.util.Optional;

public class ReduceAmountItemMechanic extends ItemMechanic {
	private final PlaceholderInt amount;

	public ReduceAmountItemMechanic(MythicLineConfig mlc) {
		super(mlc);
		this.amount = mlc.getPlaceholderInteger(new String[] { "amount", "a" }, 1);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.setAmount(item.getAmount() - amount.get(target));
		return Optional.empty();
	}
}