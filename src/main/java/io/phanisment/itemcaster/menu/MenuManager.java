package io.phanisment.itemcaster.menu;

import io.phanisment.itemcaster.menu.item.ItemBrowseMenu;

public class MenuManager {
	public static final MainMenu MAIN = new MainMenu();
	public static final ItemBrowseMenu ITEM_BROWSE = new ItemBrowseMenu();

	public static void reload() {
		MAIN.reload();
		ITEM_BROWSE.reload();
	}
}