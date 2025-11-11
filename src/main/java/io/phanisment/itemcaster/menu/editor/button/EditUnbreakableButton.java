package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.List;

public class EditUnbreakableButton extends ItemEditorButton {
	public EditUnbreakableButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.BEDROCK)
		.name("<color:#32ed73>Unbreakable")
		.lore(context -> List.of("", "<color:#e5ed4e>Current:</color> <white>" + context.getItem().isUnbreakable()))
		.click((context, player) -> {
			MythicItem item = context.getItem();
			item.getConfig().setSave("Options.Unbreakable", !item.getPreventEnchanting());
			item.loadItem();
			item.buildItemCache();
		}).build();
	}
}