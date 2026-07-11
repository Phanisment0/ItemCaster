package io.phanisment.itemcaster.menu.ability.buttons;

import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.MenuData;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.ability.ItemAbilityMenuContext;

public abstract class ItemAbilityButton implements MenuData<ItemAbilityMenuContext> {
	public ItemAbilityButton() {
		MenuManager.ITEM_ABILITY_BUTTONS.add(this);
	}
	public abstract Icon<ItemAbilityMenuContext> getIcon();

	public static void playMenuClick(Player player) {
		CasterMenu.playMenuClick(player);
	}
}
