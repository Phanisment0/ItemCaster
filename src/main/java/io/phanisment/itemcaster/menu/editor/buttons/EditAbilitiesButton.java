package io.phanisment.itemcaster.menu.editor.buttons;

import java.util.List;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.ability.ItemAbilitiesMenuContext;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;

public class EditAbilitiesButton extends ItemEditorButton {

	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.BLAZE_POWDER).name("<white>Abilities").lore(List.of(
			"",
			CasterColorCode.YELLOW + "▪ <gray>Click to see all abilities"
		)).click((ctx, p) -> {
			playMenuClick(p);
			MenuManager.ITEM_ABILITIES.open(p, new ItemAbilitiesMenuContext(ctx.item(), ctx));
		}).build();
	}
}