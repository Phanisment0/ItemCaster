package io.phanisment.itemcaster.menu.editor.buttons;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

import java.util.List;

public class EditDisplayButton extends ItemEditorButton {
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.OAK_SIGN).name("<white>Set Name").lore(ctx -> List.of(
			"<gray>Current: <white>" + ctx.mmi().getDisplayName(),
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Change Name",
			CasterColorCode.MAGENTA + "☈ <gray>R - Unset"
		)).click((ctx, p) -> {
			playMenuClick(p);
			p.closeInventory();
			CasterLogger.send(p, "<yellow>Enter the new name for the item. Type 'cancel' to cancel.");
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				ctx.item().setName(i);
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
			ctx.item().removeName();
			ctx.open(p);
		}).build();
	}
}
