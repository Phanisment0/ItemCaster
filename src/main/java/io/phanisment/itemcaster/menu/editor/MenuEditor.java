package io.phanisment.itemcaster.menu.editor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;

import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.mrmicky.fastinv.PaginatedFastInv;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.MenuBrowser;
import io.phanisment.itemcaster.menu.editor.button.IEditorButton;
import io.phanisment.itemcaster.util.Legacy;

public class MenuEditor extends PaginatedFastInv {
	private static final InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');
	public static final List<IEditorButton> buttons = new ArrayList<>();

	public MenuEditor(Optional<CasterItem> item) {
		super(54, "Item Editor");

		previousPageItem(52, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Previous")).build());
		nextPageItem(53, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Next")).build());
		
		setItem(45, new ItemBuilder(Material.BARRIER).name(Legacy.serializer("<white>Back")).build(), e -> {
			Player player = (Player) e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			new MenuBrowser().open(player);
		});
		
		setItem(49, item.get().getItemStack(), e -> {
			Player player = (Player) e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			player.getInventory().addItem(item.get().getItemStack());
		});
		
		for (IEditorButton button : buttons) {
			if (!item.isPresent()) continue;
			addContent(button.icon(item.get()).build(), e -> {
				Player player = (Player)e.getWhoClicked();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
				button.click(e, item.get());
				switch (e.getClick()) {
					case LEFT:
						button.left(e, item.get());
						break;
					case SHIFT_LEFT:
						button.shiftLeft(e, item.get());
						break;
					case RIGHT:
						button.right(e, item.get());
						break;
					case SHIFT_RIGHT:
						button.shiftRight(e, item.get());
					default:
						break;
				}
			});
		}
		scheme.apply(this);
	}
}