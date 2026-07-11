package io.phanisment.itemcaster.menu.editor.buttons;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.numbers.Numbers;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

import java.util.List;
import java.util.Optional;
import org.bukkit.Material;

public class EditDurabilityButton extends ItemEditorButton {

	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.ANVIL).name("Set Durability").lore(ctx -> List.of(
				"<gray>Current: <white>" + ctx.item().getDurability(),
				"",
				CasterColorCode.ORANGE + "✎ <gray>L - Set Durability",
				CasterColorCode.MAGENTA + "☈ <gray>R - Unset"
		)).click((ctx, p) -> {
			playMenuClick(p);
			CasterLogger.send(p, "Type in the Durability you'd like to use.\nType cancel to abort editing.");
			p.closeInventory();
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return Response.ACCEPTED;
				Optional<Integer> value = Numbers.parseIntegerOpt(i);
				if (value.isEmpty()) {
					CasterLogger.send(p, "Failed to set Durability: invalid value specified.");
					return Response.TRY_AGAIN;
				}
				ctx.item().setDurability(value.get());
				return Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			ctx.item().removeDurability();
		}).build();
	}
}
