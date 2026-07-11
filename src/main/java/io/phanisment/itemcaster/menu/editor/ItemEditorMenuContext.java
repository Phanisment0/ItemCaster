package io.phanisment.itemcaster.menu.editor;

import org.bukkit.entity.Player;

import io.lumine.mythic.core.items.MythicItem;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.item.ItemBrowseMenuContext;

public record ItemEditorMenuContext(CasterItem item, ItemBrowseMenuContext previous_menu) {
	public void open(Player player) {
		MenuManager.ITEM_EDITOR.open(player, this);
	}

	public void openPrevious(Player player) {
		MenuManager.ITEM_BROWSE.open(player, previous_menu);
	}

	public MythicItem mmi() {
		return item.getItem();
	}
}