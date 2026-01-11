package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu;
import io.phanisment.itemcaster.util.Legacy;

public class AbilitiesEditorButton implements IEditorButton {

	@Override
	public ItemBuilder icon(CasterItem item) {
		return new ItemBuilder(Material.BLAZE_POWDER)
			.name(Legacy.serializer("<white>Edit Abilities"))
			.lore(Legacy.serializer(
				"",
				"<gray>Click to edit"
			));
	}

	@Override
	public void click(InventoryClickEvent e, CasterItem item) {
		new AbilitiesMenu(item).open((Player)e.getWhoClicked());
	}
}
