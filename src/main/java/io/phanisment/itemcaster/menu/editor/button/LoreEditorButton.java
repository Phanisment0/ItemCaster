package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;

import java.util.List;
import java.util.Optional;

public class LoreEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem item) {
		return new ItemBuilder(Material.PAPER)
			.name(Legacy.serializer("<white>Edit Lore"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to change lore",
				"<gray>Right - Click to reset"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item) {
		Player player = (Player) e.getWhoClicked();
		player.closeInventory();
		CasterLogger.send(player, "<color:yellow>Enter the new lore for the item. Type 'cancel' to cancel.");
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			List<String> lore = List.of(i.split("\\n")).stream().map(String::trim).map(Legacy::serializer).toList();
			item.setLore(lore);
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new MenuEditor(Optional.of(item)).open(player));
	}

	public void right(InventoryClickEvent e, CasterItem item) {
		item.removeLores();
	}
}
