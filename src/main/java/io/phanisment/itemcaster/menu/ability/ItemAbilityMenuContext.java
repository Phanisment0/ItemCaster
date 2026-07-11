package io.phanisment.itemcaster.menu.ability;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.skill.SkillAttribute;

public record ItemAbilityMenuContext(CasterItem item, ItemAbilitiesMenuContext previous_context, SkillAttribute attribute, int index) {
	public void open(Player player) {
		MenuManager.ITEM_ABILITY.open(player, this);
	}

	public void openPrevious(Player player) {
		MenuManager.ITEM_ABILITIES.open(player, previous_context);
	}
}