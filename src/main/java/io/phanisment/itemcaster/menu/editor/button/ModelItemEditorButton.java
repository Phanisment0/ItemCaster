package io.phanisment.itemcaster.menu.editor.button;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.Constants;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;

public class ModelItemEditorButton implements IEditorButton {
	@Override
	public ItemBuilder icon(CasterItem tem) {
		return new ItemBuilder(Material.IRON_CHESTPLATE)
			.name(Legacy.serializer("<white>Edit ItemModel"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to change item model",
				"<gray>Right - Click to remove"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item) {
		Player player = (Player) e.getWhoClicked();
		if (!Constants.hasResourcePack()) {
			CasterLogger.send(player, "<red>You don't have any texture generator plugins, download plugin Nexo/ItemsAdder/Oraxen/CraftEngine to enable");
			return;
		}
		
		player.closeInventory();
		CasterLogger.send(player, "<yellow>Enter the new custom model for the item. Type 'cancel' to cancel.");
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			item.setModelData(i);
			item.removeModel();
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new MenuEditor(Optional.of(item)).open(player));
	}

	@Override
	public void right(InventoryClickEvent e, CasterItem item) {
		if (!Constants.hasResourcePack()) {
			CasterLogger.send((Player)e.getWhoClicked(), "<red>You don't have any texture generator plugins, download plugin Nexo/ItemsAdder/Oraxen/CraftEngine to enable");
			return;
		}
		item.removeModelData();
		new MenuEditor(Optional.of(item)).open((Player)e.getWhoClicked());
	}
}
