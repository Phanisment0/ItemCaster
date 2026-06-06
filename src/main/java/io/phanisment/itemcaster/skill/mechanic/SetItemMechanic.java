package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.phanisment.itemcaster.skill.template.ItemMechanic;
import io.phanisment.itemcaster.util.ItemUtil;

import java.util.Optional;

public class SetItemMechanic extends ItemMechanic {
	private final PlaceholderString type;
	private final PlaceholderInt amount;

	public SetItemMechanic(MythicLineConfig mlc) {
		super(mlc);
		this.type = mlc.getPlaceholderString(new String[] { "id", "i", "type", "t" }, "stone");
		this.amount = mlc.getPlaceholderInteger(new String[] { "amount", "a" }, 0);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!ItemUtil.validateItem(item)) return Optional.empty();
		ItemStack result = ItemUtil.getItem(type.get(target));
		if (result == null) return Optional.empty();
		int amount = this.amount.get(target);
		if (amount > 0) result.setAmount(amount);
		return Optional.of(result);
	}
}