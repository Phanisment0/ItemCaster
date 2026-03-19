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

import java.util.Optional;

public class MaxDurabilityEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem item) {
		return new ItemBuilder(Material.OAK_SIGN)
			.name(Legacy.serializer("<white>Edit Max Durability"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to change max durability",
				"<gray>Right - Click to reset"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item) {
		Player player = (Player) e.getWhoClicked();
		player.closeInventory();
		CasterLogger.send(player, "<color:yellow>Enter the new max durability for the item. Type 'cancel' to cancel.");
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			try {
				item.setMaxDurability(Integer.parseInt(i));
			} catch (NumberFormatException err) {
				CasterLogger.send(player, "<red>Value is not number, try again but with number value");
				return ChatPrompt.Response.TRY_AGAIN;
			}
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new MenuEditor(Optional.of(item)).open(player));
	}

	public void right(InventoryClickEvent e, CasterItem item) {
		item.removeMaxDurability();
	}
}
