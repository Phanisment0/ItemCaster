package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author="Phanisment", name="clearenchantmentsitem", description="Add amount item", aliases={
	"itemcaster:clearenchantmentsitem",
	"itemcaster:clearenchantments",
	"clearenchantmentsitem",
	"clearenchantments",
	"clearenchant"
})
public class ClearEnchantmentItemMechanic extends ItemMechanic {
	public ClearEnchantmentItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
	}
	
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.removeEnchantments();
		return Optional.empty();
	}
}