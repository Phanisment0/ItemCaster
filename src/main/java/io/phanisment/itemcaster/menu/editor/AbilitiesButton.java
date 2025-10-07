package io.phanisment.itemcaster.menu.editor;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;
import org.bukkit.Material;

public class AbilitiesButton extends ItemEditorButton {
	public AbilitiesButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.BLAZE_POWDER).click((context, player) -> {
			MythicItem item = context.getItem();
		}).build();
	}
}