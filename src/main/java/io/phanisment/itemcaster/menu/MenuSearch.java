package io.phanisment.itemcaster.menu;

import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.inventory.ItemStack;

import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.mrmicky.fastinv.PaginatedFastInv;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.items.MythicItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.Legacy;

public class MenuSearch extends PaginatedFastInv {
	private static final InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');
	
	public MenuSearch(String input) {
		super(54, "Item Searching...");

		previousPageItem(52, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<reset>Previous")).build());
		nextPageItem(53, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<reset>Next")).build());

		setItem(45, new ItemBuilder(Material.BARRIER).name(Legacy.serializer("<reset>Back")).build(), e -> {
			Player player = (Player) e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			new MenuBrowser().open(player);
		});

		Collection<MythicItem> mis = ItemCaster.core().getItemManager().getItems().stream().filter(item -> item.getInternalName().toLowerCase().contains(input.toLowerCase())).toList();

		for (MythicItem item : mis) {
			ItemStack is = BukkitAdapter.adapt(item.generateItemStack(1));
			addContent(new ItemBuilder(is).addLore(Legacy.serializer(
				"",
				"<gray>Left - Click to edit",
				"<gray>Right - Click to remove"
			)).build(), e -> {
				Player player = (Player) e.getWhoClicked();
				if (e.getClick().equals(ClickType.LEFT)) {
					player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
					new MenuEditor(CasterItem.getCasterItem(item)).open(player);
				} else if (e.getClick().equals(ClickType.RIGHT)) {
					ItemCaster.core().getItemManager().deleteItem(item);
					new MenuBrowser().open(player);
				}
			});
		}

		scheme.apply(this);
	}
}
