package io.phanisment.itemcaster.menu.editor.ability;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.mrmicky.fastinv.PaginatedFastInv;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu.AbilityMenuContext;
import io.phanisment.itemcaster.menu.editor.ability.button.IAbilityButton;
import io.phanisment.itemcaster.util.Legacy;

public class AbilityMenu extends PaginatedFastInv {
	private static final InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');
	public static final List<IAbilityButton> buttons = new ArrayList<>();

	public AbilityMenu(CasterItem item, AbilityMenuContext ctx) {
		super(54, "Item Ability slot: " + ctx.index());
		
		setItem(45, new ItemBuilder(Material.BARRIER).name(Legacy.serializer("<white>Back")).build(), e -> {
			Player player = (Player) e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			new AbilitiesMenu(item).open(player);
		});
		
		ItemStack is = item.getItemStack();
		setItem(49, is, e -> {
			Player player = (Player)e.getWhoClicked();
			player.getInventory().addItem(is);
		});
		
		for (IAbilityButton button : buttons) {
			addContent(button.icon(item, ctx).build(), e -> {
				Player player = (Player)e.getWhoClicked();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
				button.click(e, item, ctx);
				switch (e.getClick()) {
					case LEFT:
						button.left(e, item, ctx);
						break;
					case SHIFT_LEFT:
						button.shiftLeft(e, item, ctx);
						break;
					case RIGHT:
						button.right(e, item, ctx);
						break;
					case SHIFT_RIGHT:
						button.shiftRight(e, item, ctx);
					default:
						break;
				}
			});
		}
		scheme.apply(this);
	}
}