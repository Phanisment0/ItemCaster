package io.phanisment.itemcaster.menu.item;

import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.phanisment.itemcaster.menu.CasterMenu;

public class ItemBrowseMenu extends CasterMenu<Player> {
	public ItemBrowseMenu() {
		super("menus/item-browser.yml");
	}

	@Override
	public EditableMenuBuilder<Player> build(EditableMenuBuilder<Player> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(icon -> {
		});
		builder.getIcon("FILTER_BUTTON").ifPresent(icon -> {
		});
		builder.getIcon("FILTER_RESET_BUTTON").ifPresent(icon -> {
		});
		builder.getIcon("CREATE_BUTTON").ifPresent(icon -> {
		});
		return builder;
	}

}
