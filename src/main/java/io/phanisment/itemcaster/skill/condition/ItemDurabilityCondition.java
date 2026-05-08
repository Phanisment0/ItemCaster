package io.phanisment.itemcaster.skill.condition;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.utils.numbers.RangedInt;
import io.lumine.mythic.core.utils.annotations.MythicCondition;

@MythicCondition(author = "Phanisment", name = "itemdurability")
public class ItemDurabilityCondition extends ItemCondition {
	private final PlaceholderString amount;
	
	public ItemDurabilityCondition(String line, MythicLineConfig mlc) {
		super(line, mlc);
		amount = mlc.getPlaceholderString(new String[]{"amount", "a"}, "0");
	}


	@SuppressWarnings("unlikely-arg-type")
	@Override
	public boolean resolve(AbstractEntity target, ItemStack item) {
		if (item == null && item.getType() == Material.AIR) return true;
		if (item.getItemMeta() instanceof Damageable dmg) return new RangedInt(amount.get(target)).equals(dmg.hasDamageValue() ? dmg.getDamage() : 0);
		return true;
	}
}
