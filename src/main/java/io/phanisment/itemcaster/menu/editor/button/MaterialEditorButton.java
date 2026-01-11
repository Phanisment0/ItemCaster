package io.phanisment.itemcaster.menu.editor.button;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;

public class MaterialEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem item) {
		return new ItemBuilder(Material.GRASS_BLOCK)
			.name(Legacy.serializer("<white>Edit Material"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to change material",
				"<gray>Right - Click to reset"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item) {
		Player player = (Player) e.getWhoClicked();

		if (item.hasModelData()) {
			CasterLogger.send(player, "<red>You can't edit this, reset IemModel if you want edit this");
			return;
		}

		player.closeInventory();
		CasterLogger.send(player, "<color:yellow>Enter the new material id for the item (e.g. DIAMOND_SWORD). Type 'cancel' to cancel.");
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			if (!item.getItem().setMaterial(i.toUpperCase())) {
				CasterLogger.send(player, "Unkown material id: " + i.toUpperCase() + ", try again");
				return ChatPrompt.Response.TRY_AGAIN;
			}
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new MenuEditor(Optional.of(item)).open(player));
	}

	@Override
	public void right(InventoryClickEvent e, CasterItem item) {
		item.getItem().setMaterial("STONE");
		new MenuEditor(Optional.of(item)).open((Player)e.getWhoClicked());
	}
}