package io.phanisment.itemcaster.menu.editor;

import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.editor.buttons.ItemEditorButton;
import io.phanisment.itemcaster.menu.item.ItemBrowseMenuContext;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.entity.Player;

public class ItemEditorMenu extends CasterMenu<ItemEditorMenuContext> {

	public ItemEditorMenu() {
		super("menus/item-editor.yml");
	}

	public void open(Player p, CasterItem ci) {
		this.open(p, new ItemEditorMenuContext(ci, new ItemBrowseMenuContext()));
	}

	public void open(Player player, ItemEditorMenuContext context) {
		List<ItemEditorButton> list = new ArrayList<>(MenuManager.ITEM_EDITOR_BUTTONS.buttons());
		list.removeIf(buttom -> !buttom.isApplicable(context.item()));
		this.open(player, context, "Item: " + context.item().getId(), list);
	}

	public EditableMenuBuilder<ItemEditorMenuContext> build(EditableMenuBuilder<ItemEditorMenuContext> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctz, p) -> ctz.openPrevious(p)));
		builder.getIcon("GIVE_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> p.getInventory().addItem(ctx.item().getItemStack())));
		builder.getIcon("ITEM_BUTTON").ifPresent(icon -> icon.getBuilder().itemStack(ctx -> ctx.item().getItemStack()).hideFlags(false).click((ctx, p) -> {
			playMenuClick(p);
			p.getInventory().addItem(ctx.item().getItemStack());
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
		}));
		return builder;
	}
}
