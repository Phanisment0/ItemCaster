package io.phanisment.itemcaster.menu.item;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.menu.MenuManager;

public class ItemBrowseMenuContext {
	private String filter = null;

	public String filter() {
		return filter;
	}

	public ItemBrowseMenuContext filter(String filter) {
		this.filter = filter;
		return this;
	}

	public void open(Player player) {
		MenuManager.ITEM_BROWSE.open(player);	
	}

	public void openPrevious(Player player) {
		MenuManager.MAIN.open(player);
	}
}
