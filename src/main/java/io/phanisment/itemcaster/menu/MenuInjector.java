package io.phanisment.itemcaster.menu;

import io.lumine.mythic.core.menus.items.ItemEditorMenu;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.menu.editor.button.*;

import java.lang.reflect.Field;

public class MenuInjector {
	public static void register() {
		try {
			Field btn_field = ItemEditorMenu.class.getDeclaredField("buttons");
			btn_field.setAccessible(true);
			var edit_buttons = (ItemEditorButtons)btn_field.get(ItemCaster.core().getMenuManager().getItemEditorMenu());
			edit_buttons.buttons.clear();
			add(edit_buttons);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
	}
	
	private static void add(ItemEditorButtons handler) {
		new EditAbilitiesButton(handler, 0);
	}
}