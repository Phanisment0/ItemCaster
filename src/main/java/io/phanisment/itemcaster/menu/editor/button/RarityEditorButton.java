package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.lumine.mythic.api.adapters.AbstractItemStackRarity;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;

import java.util.Optional;

public class RarityEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem item) {
		return new ItemBuilder(Material.PAPER)
			.name(Legacy.serializer("<white>Edit Rarity"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to change rarity",
				"<gray>Right - Click to reset"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item) {
		Player player = (Player) e.getWhoClicked();
		player.closeInventory();
		CasterLogger.send(player, "<color:yellow>Enter the new lore for the item. Type 'cancel' to cancel.");
		var builder = new StringBuilder();
		for (var type : AbstractItemStackRarity.values()) builder.append(type.toString() + "\n");
		CasterLogger.send(player, "<green>" + builder.toString());
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			
			try {
				item.setRarity(AbstractItemStackRarity.valueOf(i));
			} catch (IllegalArgumentException err) {
				CasterLogger.send(player, "<red>Unknown Type, try again");
				return ChatPrompt.Response.TRY_AGAIN;
			}
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new MenuEditor(Optional.of(item)).open(player));
	}

	public void right(InventoryClickEvent e, CasterItem item) {
		item.removeRarity();
	}
}
