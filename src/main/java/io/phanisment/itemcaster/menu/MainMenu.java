package io.phanisment.itemcaster.menu;

import org.bukkit.Sound;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;

public class MainMenu extends CasterMenu<Player> {

	public MainMenu() {
		super("menus/main.yml");
	}

	public void open(Player player) {
		this.open(player, player);
	}

	@Override
	public EditableMenuBuilder<Player> build(EditableMenuBuilder<Player> builder) {
		builder = this.addPageButtons(builder);

		builder.getIcon("BUTTON_ITEMBROWSER").ifPresent(icon -> icon.getBuilder().click(MainMenu::openItemBrowse).rightClick(MainMenu::openItemBrowse));
		builder.getIcon("BUTTON_HANDBROWSER").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> p.playSound(p.getLocation(), Sound.ENTITY_VILLAGER_NO, 1f, 1f)));

		return builder;
	}

	public static void openItemBrowse(Player ctx, Player player) {
		MenuManager.ITEM_BROWSE.open(player);
		playMenuClick(player);
	}
}
