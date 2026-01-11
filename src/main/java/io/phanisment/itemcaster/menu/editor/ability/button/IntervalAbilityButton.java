package io.phanisment.itemcaster.menu.editor.ability.button;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu.AbilityMenuContext;
import io.phanisment.itemcaster.menu.editor.ability.AbilityMenu;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;

public class IntervalAbilityButton implements IAbilityButton {

	@Override
	public ItemBuilder icon(CasterItem item, AbilityMenuContext ctx) {
		return new ItemBuilder(Material.REPEATER)
		.name(Legacy.serializer("<white>Interval"))
		.lore(Legacy.serializer(
			"<dark_gray>Current: <white>" + ctx.data().getInterval(),
			"",
			"<gray>Left - Click to edit",
			"<gray>Right - Click to remove"
		));
	}
	
	@Override
	public void left(InventoryClickEvent e, CasterItem item, AbilityMenuContext ctx) {
		Player player = (Player)e.getWhoClicked();
		player.closeInventory();
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			
			try {
				ctx.data().setInterval(Integer.parseInt(i));
				item.setAbility(ctx.index(), ctx.data());
			} catch (NumberFormatException err) {
				CasterLogger.send(player, "<red>Input must be number, try agin");
				return ChatPrompt.Response.TRY_AGAIN;
			}
			
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
	}
	
	@Override
	public void right(InventoryClickEvent e, CasterItem item, AbilityMenuContext ctx) {
		ctx.data().setInterval(null);
		item.setAbility(ctx.index(), ctx.data());
		new AbilityMenu(item, ctx).open((Player)e.getWhoClicked());
	}
}