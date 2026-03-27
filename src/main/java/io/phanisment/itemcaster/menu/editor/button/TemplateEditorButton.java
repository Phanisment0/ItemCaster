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

public class TemplateEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem item) {
		return new ItemBuilder(Material.SENTRY_ARMOR_TRIM_SMITHING_TEMPLATE)
			.name(Legacy.serializer("<white>Edit Template"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to change template",
				"<gray>Right - Click to reset"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item) {
		Player player = (Player) e.getWhoClicked();
		player.closeInventory();
		CasterLogger.send(player, "<color:yellow>Enter the template for the item. Type 'cancel' to cancel.");
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			item.setTemplate(i);
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new MenuEditor(Optional.of(item)).open(player));
	}

	public void right(InventoryClickEvent e, CasterItem item) {
		item.removeTemplate();
	}
}
