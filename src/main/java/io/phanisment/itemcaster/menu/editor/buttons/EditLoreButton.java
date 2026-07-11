package io.phanisment.itemcaster.menu.editor.buttons;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * Need Improvement instead of just adding new line
 * or remove last line.
 */
public class EditLoreButton extends ItemEditorButton {

	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.WRITABLE_BOOK).name("<white>Set Lore").lore(ctx -> {
			List<String> list = new ArrayList<>();
			List<String> lore = ctx.item().getLore();
			if (!lore.isEmpty()) {
				list.add("<gray>Current:");
				list.add("");
				list.addAll(lore);
			} else list.add("<gray>Current: <white>null");
			list.addAll(List.of(
				"",
				CasterColorCode.ORANGE + "✎ <gray>L - Add Lore",
				CasterColorCode.MAGENTA + "☈ <gray>R - Remove Last Lore"
			));
			return list;
		}).click((ctx, p) -> {
			playMenuClick(p);
			p.closeInventory();
			CasterLogger.send(p, "<yellow>Enter the new line lore for the item. Type 'cancel' to cancel.");
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				List<String> lore = ctx.item().getLore();
				lore.add(i);
				ctx.item().setLore(lore);
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
			ctx.item().removeLore(ctx.item().getLore().size() - 1);
			ctx.open(p);
		}).build();
	}
}
