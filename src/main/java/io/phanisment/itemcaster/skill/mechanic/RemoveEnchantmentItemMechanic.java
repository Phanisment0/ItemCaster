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

@MythicMechanic(author="Phanisment", name="removeenchantmentitem", description="Add amount item", aliases={
	"itemcaster:removeenchantmentitem",
	"itemcaster:removeenchantitem",
	"itemcaster:removeenchant",
	"removeenchantitem",
	"removeenchant"
})
public class RemoveEnchantmentItemMechanic extends ItemMechanic {
	private Enchantment enchantment;
	
	@SuppressWarnings("deprecation")
	public RemoveEnchantmentItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		String string_enc = mlc.getString(new String[]{"enchantment", "en"}, "UNBREAKING").toUpperCase();
		this.enchantment = Enchantment.getByName(string_enc);
	}
	
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.removeEnchantment(enchantment);
		return Optional.empty();
	}
}