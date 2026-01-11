package io.phanisment.itemcaster.menu.editor.ability.button;

import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu.AbilityMenuContext;

public interface IAbilityButton {
 ItemBuilder icon(CasterItem item, AbilityMenuContext ctx);

 default void click(InventoryClickEvent event, CasterItem item, AbilityMenuContext ctx) {
	}

	default void left(InventoryClickEvent event, CasterItem item, AbilityMenuContext ctx) {
	}

	default void shiftLeft(InventoryClickEvent event, CasterItem item, AbilityMenuContext ctx) {
	}

	default void right(InventoryClickEvent event, CasterItem item, AbilityMenuContext ctx) {
	}

	default void shiftRight(InventoryClickEvent event, CasterItem item, AbilityMenuContext ctx) {
	}
}