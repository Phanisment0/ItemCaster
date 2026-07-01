package io.phanisment.itemcaster.menu;

import io.lumine.mythic.bukkit.utils.config.properties.Property;
import io.lumine.mythic.bukkit.utils.config.properties.types.DoubleProp;
import io.lumine.mythic.bukkit.utils.config.properties.types.MenuProp;
import io.lumine.mythic.bukkit.utils.config.properties.types.StringProp;
import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.lumine.mythic.bukkit.utils.menu.MenuBuilder;
import io.lumine.mythic.bukkit.utils.menu.ReloadableMenu;
import io.lumine.mythic.core.config.Scope;

import static io.phanisment.itemcaster.ItemCaster.inst;

import org.bukkit.entity.Player;

public abstract class CasterMenu<T> extends ReloadableMenu<T> {
	public static final StringProp MENU_CLICK_SOUND;
	public static final DoubleProp MENU_CLICK_PITCH;
	public static final DoubleProp MENU_CLICK_VOLUME;

	public CasterMenu(String path) {
		super(new MenuProp<T>(inst(), inst().getPropertyFileInternal(inst().getResource(path)), "Menu", (MenuBuilder<T>)null));
	}

	public CasterMenu(String path, boolean build_on_open) {
		super(new MenuProp<T>(inst(), inst().getPropertyFileInternal(inst().getResource(path)), "Menu", (MenuBuilder<T>)null), build_on_open);
	}

	public EditableMenuBuilder<T> addPageButtons(EditableMenuBuilder<T> builder) {
		builder.getIcon("NEXT_PAGE").ifPresent((icon) -> {
			icon.getBuilder().click((profile, player) -> {
				player.playSound(player.getLocation(), "item.book.page_turn", 1.0F, 1.0F);
				this.nextPage(player);
			});
		});
		builder.getIcon("PREVIOUS_PAGE").ifPresent((icon) -> {
			icon.getBuilder().click((profile, player) -> {
				player.playSound(player.getLocation(), "item.book.page_turn", 1.0F, 1.0F);
				this.previousPage(player);
			});
		});
		return builder;
	}

	public final void playMenuClick(Player player) {
		double volume = (Double)MENU_CLICK_VOLUME.get();
		double pitch = (Double)MENU_CLICK_PITCH.get();
		player.playSound(player.getLocation(), (String)MENU_CLICK_SOUND.get(), (float)volume, (float)pitch);
	}

	static {
		MENU_CLICK_SOUND = Property.String(Scope.CONFIG_GENERAL, "Menus.ButtonClick.Sound", "item.armor.equip_turtle");
		MENU_CLICK_PITCH = Property.Double(Scope.CONFIG_GENERAL, "Menus.ButtonClick.Pitch", 2.0D);
		MENU_CLICK_VOLUME = Property.Double(Scope.CONFIG_GENERAL, "Menus.ButtonClick.Volume", 0.4D);
	}
}
