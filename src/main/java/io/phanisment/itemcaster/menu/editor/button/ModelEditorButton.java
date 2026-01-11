package io.phanisment.itemcaster.menu.editor.button;

import fr.mrmicky.fastinv.ItemBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;
import java.util.Optional;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class ModelEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem tem) {
		return new ItemBuilder(Material.PAINTING)
			.name(Legacy.serializer("<white>Edit Custom Model Data"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to change item model",
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
		CasterLogger.send(player, "<color:yellow>Enter the new model number for the item. Type 'cancel' to cancel.");
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			try {
				int id = Integer.parseInt(i);
				item.setModel(id);
			} catch (NumberFormatException err) {
				CasterLogger.send(player, "<red>The value must be number, try again using number");
				return ChatPrompt.Response.TRY_AGAIN;
			}
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new MenuEditor(Optional.of(item)).open(player));
	}

	@Override
	public void right(InventoryClickEvent e, CasterItem item) {
		item.removeModel();
		new MenuEditor(Optional.of(item)).open((Player)e.getWhoClicked());
	}
}
