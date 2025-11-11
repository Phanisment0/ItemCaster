package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.AbilitiesMenu;

import java.util.List;
import java.util.Optional;

public class EditAbilitiesButton extends ItemEditorButton {
	public EditAbilitiesButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.BLAZE_POWDER)
		.name("<color:#32ed73>View Item Abilities")
		.lore(ctx -> {
			CasterItem item = CasterItem.getCasterItem(ctx.getItem()).get();
			return List.of(
				"<dark_grey>From plugin ItemCaster",
				"<color:#e5ed4e>Current:</color> <white>" + item.getAbilities().size() + " slots",
				"",
				"<gray>Click to view"
			);
		}).click((ctx, player) -> {
			Optional<CasterItem> item = CasterItem.getCasterItem(ctx.getItem());
			if (!item.isPresent()) {
				CommandHelper.sendError((CommandSender)player, "Unexpected Error!");
				return;
			}
			new AbilitiesMenu(item.get()).open(player);
		}).build();
	}
}