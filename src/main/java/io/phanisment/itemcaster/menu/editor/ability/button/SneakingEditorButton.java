package io.phanisment.itemcaster.menu.editor.ability.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu.AbilityMenuContext;
import io.phanisment.itemcaster.menu.editor.ability.AbilityMenu;
import io.phanisment.itemcaster.util.Legacy;

public class SneakingEditorButton implements IAbilityButton {

	@Override
	public ItemBuilder icon(CasterItem item, AbilityMenuContext ctx) {
		return new ItemBuilder(Material.BEDROCK)
			.name(Legacy.serializer("<white>Sneaking"))
			.lore(Legacy.serializer(
				"", 
				"<gray>Left - Click to toggle (Current: <white>" + (ctx.data().getSneaking() ? "True" : "False") + "<gray>)",
				"<gray>Right - Click to remove"
			));
	}

	@Override
	public void left(InventoryClickEvent e, CasterItem item, AbilityMenuContext ctx) {
		ctx.data().setSneaking(!ctx.data().getSneaking());
		item.setAbility(ctx.index(), ctx.data());
		new AbilityMenu(item, ctx).open((Player)e.getWhoClicked());
	}

	@Override
	public void right(InventoryClickEvent e, CasterItem item, AbilityMenuContext ctx) {
		ctx.data().setSneaking(null);
		item.setAbility(ctx.index(), ctx.data());
		new AbilityMenu(item, ctx).open((Player)e.getWhoClicked());
	}
}
