package io.phanisment.itemcaster.menu.editor.buttons;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.MenuData;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;

import java.util.function.Predicate;

import org.bukkit.entity.Player;

public abstract class ItemEditorButton implements MenuData<ItemEditorMenuContext> {
	public ItemEditorButton() {
		MenuManager.ITEM_EDITOR_BUTTONS.add(this);
	}

	public abstract Icon<ItemEditorMenuContext> getIcon();

	public boolean isApplicable(CasterItem item) {
		return true;
	}

	public boolean isApplicable(CasterItem item, Predicate<CasterItem> predicate) {
		return predicate.test(item);
	}

	public static void playMenuClick(Player player) {
		CasterMenu.playMenuClick(player);
	}
}