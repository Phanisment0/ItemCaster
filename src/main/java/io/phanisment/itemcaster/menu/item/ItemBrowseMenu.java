package io.phanisment.itemcaster.menu.item;

import static io.phanisment.itemcaster.ItemCaster.core;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.bukkit.entity.Player;

import io.lumine.mythic.api.packs.Pack;
import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.lumine.mythic.core.items.MythicItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.util.CasterLogger;

public class ItemBrowseMenu extends CasterMenu<ItemBrowseMenuContext> {
	private static final Comparator<CasterItem> ITEM_SORT = Comparator.comparing(CasterItem::getId, String.CASE_INSENSITIVE_ORDER);

	public ItemBrowseMenu() {
		super("menus/item-browse.yml");
	}

	public void open(Player player) {
		this.open(player, new ItemBrowseMenuContext());
	}

	@Override
	public void open(Player player, ItemBrowseMenuContext ctx) {
		List<CasterItem> items = new ArrayList<>(CasterItem.ITEMS.values());
		if (ctx.filter() != null) items.removeIf(ci -> !ci.getId().toLowerCase().contains(ctx.filter().toLowerCase()));
		items.sort(ITEM_SORT);
		this.open(player, ctx, items);
	}

	@Override
	public EditableMenuBuilder<ItemBrowseMenuContext> build(EditableMenuBuilder<ItemBrowseMenuContext> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> MenuManager.MAIN.open(p)));
		builder.getIcon("FILTER_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> {
			playMenuClick(p);
			p.closeInventory();
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return Response.ACCEPTED;
				ctx.filter(i);
				return Response.ACCEPTED;
			}).thenAcceptSync(in -> this.open(p, ctx));
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
			ctx.filter(null);
			this.open(p, ctx);
		}));
		builder.getIcon("CREATE_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> {
			playMenuClick(p);
			createItem(p, ctx);
		}));
		return builder;
	}

	private void createItem(Player player, ItemBrowseMenuContext ctx) {
		player.closeInventory();
		CasterLogger.send(player, "Enter id and pack for the new item, type <yellow>'cancel'</yellow> to cancle");
		CasterLogger.send(player, "<yellow>Type the folowing orders: <item_name> [pack] [file_name]");
		ChatPrompt.listen(player, i -> handlerCreateItem(player, i, ctx)).thenAcceptSync(in -> ctx.open(player));
	}

	private ChatPrompt.Response handlerCreateItem(Player player, String i, ItemBrowseMenuContext ctx) {
		if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
		String[] parts = i.split(" ");

		if (parts.length == 0 || parts[0].isEmpty()) {
			CasterLogger.send(player, "<red>Item name is required");
			return ChatPrompt.Response.TRY_AGAIN;
		}

		String name = parts[0];
		String pack_name = "";
		String file_name = "itemcaster_generated_items";

		File file_loc;

		if (core().getItemManager().getItems().stream().anyMatch(mi -> mi.getInternalName().contains(name))) {
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
			core().getItemManager().registerItem(name, item);
			new CasterItem(item);
		} catch (IOException err) {
			CasterLogger.send(player, "<red>Error when creating the file");
		}
		return ChatPrompt.Response.ACCEPTED;
	}
}