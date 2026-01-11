package io.phanisment.itemcaster.menu.editor.button;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.Legacy;

public class PreventAnvilEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem item) {
		return new ItemBuilder(Material.ANVIL)
			.name(Legacy.serializer("<white>Prevent Anvil"))
			.lore(Legacy.serializer(
				"", 
				"<gray>Left - Click to toggle (Current: <white>" + (item.isPreventAnvil() ? "True" : "False") + "<gray>)",
				"<gray>Right - Click to remove"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item) {
		Player player = (Player)e.getWhoClicked();
		item.setPreventAnvil(!item.isPreventAnvil());
		new MenuEditor(Optional.of(item)).open(player);
	}

	@Override
	public void right(InventoryClickEvent e, CasterItem item) {
		Player player = (Player)e.getWhoClicked();
		item.removePreventAnvil();
		new MenuEditor(Optional.of(item)).open(player);
	}
}
