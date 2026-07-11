package io.phanisment.itemcaster.menu.ability.buttons;

import java.util.List;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.ability.ItemAbilityMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;

public class EditVariableButton extends ItemAbilityButton {

	@Override
	public Icon<ItemAbilityMenuContext> getIcon() {
		return IconBuilder.<ItemAbilityMenuContext>create().material(Material.ENDER_CHEST).name("Variables").lore(List.of(
			"",
			CasterColorCode.YELLOW + "▪ <gray>Click to see all variables"
		)).click((ctx, p) -> {
			playMenuClick(p);
			MenuManager.ABILITY_VARIABLE_LIST_MENU.open(p, ctx);
		}).build();
	}
}
