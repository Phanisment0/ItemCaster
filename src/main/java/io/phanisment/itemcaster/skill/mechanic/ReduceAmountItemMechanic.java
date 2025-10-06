package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author="Phanisment", name="reduceamountitem", description="Reduce amount item")
public class ReduceAmountItemMechanic extends ItemMechanic {
	private int amount;
	
	public ReduceAmountItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		this.amount = mlc.getInteger(new String[]{"amount", "a"}, 1);
	}
	
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.setAmount(item.getAmount() - amount);
		return Optional.empty();
	}
}