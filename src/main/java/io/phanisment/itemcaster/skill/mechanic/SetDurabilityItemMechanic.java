package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author="Phanisment", name="setdurabilityitem", description="Set durability item")
public class SetDurabilityItemMechanic extends ItemMechanic {
	private int amount;
	
	public SetDurabilityItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		this.amount = mlc.getInteger(new String[]{"amount", "a"}, 1);
	}
	
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		ItemMeta meta = item.getItemMeta();
		if (meta instanceof Damageable dmg) {
			dmg.setDamage(amount);
			item.setItemMeta(dmg);
			return Optional.of(item);
		}
		return Optional.empty();
	}
}