package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.List;

public class EditRepairableButton extends ItemEditorButton {
	public EditRepairableButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}

	@Override
	public boolean isApplicable(MythicItem mythicItem) {
		return !mythicItem.isUnbreakable() && mythicItem.getMaterial().getMaxDurability() > 0;
	}

	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.ENCHANTED_BOOK)
		.name("<color:#32ed73>Is Repairable")
		.lore(context -> List.of("", "<color:#e5ed4e>Current:</color> <white>" + !context.getItem().getPreventAnvilWith()))
		.click((context, player) -> {
			MythicItem item = context.getItem();
			item.getConfig().setSave("Options.PreventAnvil", !item.getPreventAnvilWith());
			item.loadItem();
			item.buildItemCache();
		}).build();
	}
}

