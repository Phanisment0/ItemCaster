package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.List;

public class EditEnchantableButton extends ItemEditorButton {
	public EditEnchantableButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}

	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.ENCHANTED_BOOK)
		.name("<color:#32ed73>Is Enchantable")
		.lore(context -> List.of(
			"",
			"<color:#e5ed4e>Current:</color> <white>" + !context.getItem().getPreventEnchanting(),
			"",
			"<gray>Click to toggle",
			"<gray>Shift-Click to unset"
		)).click((context, player) -> {
			MythicItem item = context.getItem();
			item.getConfig().setSave("Options.PreventEnchanting", !item.getPreventEnchanting());
			item.loadItem();
			item.buildItemCache();
		}).shiftClick((context, player) -> {
			MythicItem item = context.getItem();
			item.getConfig().unsetSave("Options.PreventEnchanting");
			item.loadItem();
			item.buildItemCache();
		}).build();
	}
}

