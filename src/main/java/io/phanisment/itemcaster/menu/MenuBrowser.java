package io.phanisment.itemcaster.menu;

import static io.phanisment.itemcaster.ItemCaster.core;
import static io.phanisment.itemcaster.ItemCaster.inst;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.mrmicky.fastinv.PaginatedFastInv;
import io.lumine.mythic.api.packs.Pack;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.items.MythicItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;

public class MenuBrowser extends PaginatedFastInv {
	private static final InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');
	private final Collection<MythicItem> mis;

	public MenuBrowser() {
		super(54, "Item Browser");
		mis = core().getItemManager().getItems();
		previousPageItem(52, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Previous")).build());
		nextPageItem(53, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Next")).build());

		setItem(48, new ItemBuilder(Material.NETHER_STAR).name(Legacy.serializer("<white>Create new Item")).build(), this::createItem);

		setItem(49, new ItemBuilder(Material.SPYGLASS)
			.name(Legacy.serializer("<white>Search"))
			.lore(Legacy.serializer(
				"",
				"<gray>Left - Click to Search item",
				"<gray>Right - Click to edit item with id"
			)).build(), e -> {
				Player player = (Player) e.getWhoClicked();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
				player.closeInventory();
				if (e.getClick().equals(ClickType.RIGHT)) {
					CasterLogger.send(player, "Enter item id to edit, type 'cancel' to cancel");
					ChatPrompt.listen(player, i -> {
						Optional<CasterItem> ic = CasterItem.getCasterItem(i);
						if (i.equalsIgnoreCase("cancel")) {
							CasterLogger.send(player, "<red>Cancelled!");
							Bukkit.getScheduler().runTask(inst(), () -> new MenuEditor(ic).open(player));
							return ChatPrompt.Response.ACCEPTED;
						}
						
						if (!ic.isPresent()) {
							CasterLogger.send(player, "<red>Item not found! try again");
							return ChatPrompt.Response.TRY_AGAIN;
						}
						Bukkit.getScheduler().runTask(inst(), () -> new MenuEditor(ic).open(player));
						return ChatPrompt.Response.ACCEPTED;
					});
				} else if (e.getClick().equals(ClickType.LEFT)) {
					CasterLogger.send(player, "Enter the text that contains or match item id, type 'cancel' to cancel");
					ChatPrompt.listen(player, i -> {
						if (i.equalsIgnoreCase("cancel")) {
							CasterLogger.send(player, "<red>Cancelled!");
							Bukkit.getScheduler().runTask(inst(), () -> new MenuBrowser().open(player));
							return ChatPrompt.Response.ACCEPTED;
						}
						Bukkit.getScheduler().runTask(inst(), () -> new MenuSearch(i).open(player));
						return ChatPrompt.Response.ACCEPTED;
					});
				}
			});
		
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
					core().getItemManager().deleteItem(item);
					new MenuBrowser().open(player);
				}
			});
		}
		scheme.apply(this);
	}

	private void createItem(InventoryClickEvent e) {
		Player player = (Player)e.getWhoClicked();
		player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
		player.closeInventory();
		CasterLogger.send(player, "Enter id and pack for the new item, type <yellow>'cancel'</yellow> to cancle");
		CasterLogger.send(player, "<yellow>Type the folowing orders: <item_name> [pack] [file_name]");
		ChatPrompt.listen(player, i -> handlerCreateItem(player, i)).thenAcceptSync(in -> new MenuBrowser().open(player));
	}

	private ChatPrompt.Response handlerCreateItem(Player player, String i) {
		if (i.equalsIgnoreCase("cancel")) {
			CasterLogger.send("<green>Cancelled!");
			return ChatPrompt.Response.ACCEPTED;
		}

		String[] parts = i.split(" ");

		if (parts.length == 0 || parts[0].isEmpty()) {
			CasterLogger.send(player, "<red>Item name is required");
			return ChatPrompt.Response.TRY_AGAIN;
		}

		String name = parts[0];
		String pack_name = "";
		String file_name = "itemcaster_generated_items";

		File file_loc;

		if (mis.stream().anyMatch(item -> item.getInternalName().equals(name))) {
			CasterLogger.send(player, "<red>Item name has ben exist, use another name for create the item");
			return ChatPrompt.Response.TRY_AGAIN;
		}

		if (parts.length >= 2) pack_name = parts[1];
		if (parts.length >= 3) file_name = parts[2];

		if (file_name.contains(".") || file_name.contains("/")) {
			CasterLogger.send(player, "<red>You can't use '.' or '/' to name the file, try again");
			return ChatPrompt.Response.TRY_AGAIN;
		}

		Pack pack = ItemCaster.core().getPackManager().getPack(pack_name).orElse(null);
		if (pack == null) {
			CasterLogger.send(player, "Pack not found, try again");
			return ChatPrompt.Response.TRY_AGAIN;
		}

		file_loc = new File(pack.getFolder(), "items/" + file_name + ".yml");
		try {
			if (!file_loc.exists()) file_loc.createNewFile();
			MythicItem item = new MythicItem(pack, file_loc, name);
			ItemCaster.core().getItemManager().registerItem(name, item);
			new CasterItem(item);
		} catch (IOException err) {
			CasterLogger.send(player, "<red>Error when creating the file");
		}
		return ChatPrompt.Response.ACCEPTED;
	}
}