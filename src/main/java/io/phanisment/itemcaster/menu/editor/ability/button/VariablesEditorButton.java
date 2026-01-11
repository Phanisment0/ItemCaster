package io.phanisment.itemcaster.menu.editor.ability.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu.AbilityMenuContext;
import io.phanisment.itemcaster.menu.editor.ability.VariablesMenu;
import io.phanisment.itemcaster.util.Legacy;

public class VariablesEditorButton implements IAbilityButton {

	@Override
	public ItemBuilder icon(CasterItem item, AbilityMenuContext ctx) {
		return new ItemBuilder(Material.CHEST)
			.name(Legacy.serializer("<white>Variable"))
			.lore(Legacy.serializer(
				"", 
				"<gray>Click to edit"
			));
	}

	@Override
	public void click(InventoryClickEvent e, CasterItem item, AbilityMenuContext ctx) {
		new VariablesMenu(item, ctx).open((Player)e.getWhoClicked());
	}
}
