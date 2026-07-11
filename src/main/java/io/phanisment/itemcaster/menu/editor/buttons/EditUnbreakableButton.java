package io.phanisment.itemcaster.menu.editor.buttons;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;

import java.util.List;

import org.bukkit.Material;

public class EditUnbreakableButton extends ItemEditorButton {

	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.BEDROCK).name("Toggle Unbreakable").lore(ctx -> List.of(
			"<gray>Current: <white>" + ctx.item().isUnbreakable(),
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Toggle",
			CasterColorCode.MAGENTA + "☈ <gray>R - Unset"
		)).click((ctx, p) -> {
			ctx.item().setUnbreakable(!ctx.item().isUnbreakable());
			ctx.open(p);
		}).rightClick((ctx, p) -> {
			ctx.item().removeUnbreakable();
			ctx.open(p);
		}).build();
	}
}
