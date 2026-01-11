package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.phanisment.itemcaster.item.CasterItem;

public interface IEditorButton {
	ItemBuilder icon(CasterItem item);

	default void click(InventoryClickEvent event, CasterItem item) {
	}

	default void left(InventoryClickEvent event, CasterItem item) {
	}

	default void shiftLeft(InventoryClickEvent event, CasterItem item) {
	}

	default void right(InventoryClickEvent event, CasterItem item) {
	}

	default void shiftRight(InventoryClickEvent event, CasterItem item) {
	}
}
