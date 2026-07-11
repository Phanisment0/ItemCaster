package io.phanisment.itemcaster.menu.editor.buttons;

import io.lumine.mythic.api.adapters.AbstractItemStackRarity;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

import java.util.List;

import org.bukkit.Material;

public class EditRarityButton extends ItemEditorButton {

	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.DIAMOND).name("Set Rarity").lore(ctx -> List.of(
			"<gray>Current: <white>" + ctx.mmi().getRarity(),
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Change Rarity",
			CasterColorCode.MAGENTA + "☈ <gray>R - Unset"
		)).click((ctx, p) -> {
			playMenuClick(p);
			CasterLogger.send(p, "Type in the Group you'd like to assign.\nType cancel to abort editing.");
			p.closeInventory();
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return Response.ACCEPTED;
					ctx.mmi().setRarity(AbstractItemStackRarity.valueOf(i));
					return Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			ctx.item().removeRarity();
			ctx.open(p);
		}).build();
	}
}
