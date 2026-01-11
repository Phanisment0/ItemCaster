package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import io.phanisment.itemcaster.util.ItemUtil;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author = "Phanisment", name = "setitem", aliases = { "itemcaster:setitem", "itemcaster:replaceitem",
		"replaceitem" }, description = "Set slot with external item that support in ItemCaster")
public class SetItemMechanic extends ItemMechanic {
	private String type = "stone";
	private int amount = 0;

	public SetItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		this.type = mlc.getString(new String[] { "id", "i", "type", "t" }, "stone");
		this.amount = mlc.getInteger(new String[] { "amount", "a" }, 0);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!ItemUtil.validateItem(item))
			return Optional.empty();
		ItemStack result = ItemUtil.getItem(type);
		if (result == null)
			return Optional.empty();
		if (amount > 0)
			result.setAmount(amount);
		return Optional.of(result);
	}
}