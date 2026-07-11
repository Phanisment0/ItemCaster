package io.phanisment.itemcaster.menu.ability;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;

public record ItemAbilitiesMenuContext(CasterItem item, ItemEditorMenuContext previous_context) {
	public void open(Player player) {
		MenuManager.ITEM_ABILITIES.open(player, this);
	}

	public void openPrevious(Player player) {
		MenuManager.ITEM_EDITOR.open(player, previous_context);
	}
}
