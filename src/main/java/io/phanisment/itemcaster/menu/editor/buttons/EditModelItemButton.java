package io.phanisment.itemcaster.menu.editor.buttons;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

import java.util.List;

public class EditModelItemButton extends ItemEditorButton {
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.ITEM_FRAME).name("<white>Set Model Item").lore(ctx -> List.of(
			"<gray>Defrence from Item Model, this change item appearance",
			"<gray>from external item, ex = nexo:forest_axe",
			"",
			"<gray>Current: <white>" + ctx.item().getModelData().toString(),
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Change Model Item",
			CasterColorCode.MAGENTA + "☈ <gray>R - Unset"
		)).click((ctx, p) -> {
			playMenuClick(p);
			p.closeInventory();
			CasterLogger.send(p, "<yellow>Enter the new name for the item. Type 'cancel' to cancel.");
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				ctx.item().setModelData(i);
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
			ctx.item().removeModelData();
			ctx.open(p);
		}).build();
	}
}
