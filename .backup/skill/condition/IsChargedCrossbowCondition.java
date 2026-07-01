package io.phanisment.itemcaster.skill.condition;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.CrossbowMeta;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.utils.annotations.MythicCondition;
import io.phanisment.itemcaster.skill.template.ItemCondition;

@MythicCondition(author = "Phanisment", name = "iscrossbowcharged", aliases = { "crossbowcharged" }, description = "If crossbow is charged")
public class IsChargedCrossbowCondition extends ItemCondition {
	public IsChargedCrossbowCondition(MythicLineConfig mlc) {
		super(mlc);
	}

	@Override
	public boolean resolve(AbstractEntity target, ItemStack item) {
		if (item.getItemMeta() instanceof CrossbowMeta cs) return cs.hasChargedProjectiles();
		return false;
	}
}