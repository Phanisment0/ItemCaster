package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author = "Phanisment", name = "addenchantmentitem", description = "Add amount item", aliases = {
		"itemcaster:addenchantmentitem",
		"itemcaster:enchantitem",
		"itemcaster:enchant",
		"enchantitem",
		"enchant"
})
public class AddEnchantmentItemMechanic extends ItemMechanic {
	private Enchantment enchantment;
	private int level;
	private boolean ignore_restriction;

	@SuppressWarnings("deprecation")
	public AddEnchantmentItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		String string_enc = mlc.getString(new String[] { "enchantment", "en" }, "UNBREAKING").toUpperCase();
		this.enchantment = Enchantment.getByName(string_enc);
		this.level = mlc.getInteger(new String[] { "level", "l" }, 1);
		this.ignore_restriction = mlc.getBoolean(new String[] { "ignorerestriction", "ir" }, true);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item))
			return Optional.empty();
		if (ignore_restriction)
			item.addUnsafeEnchantment(enchantment, level);
		item.addEnchantment(enchantment, level);
		return Optional.empty();
	}
}