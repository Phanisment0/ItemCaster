package io.phanisment.itemcaster.menu.ability;

import java.util.ArrayList;
import java.util.List;

import io.phanisment.itemcaster.menu.ability.buttons.EditCooldownButton;
import io.phanisment.itemcaster.menu.ability.buttons.EditPowerButton;
import io.phanisment.itemcaster.menu.ability.buttons.EditShowCooldownButton;
import io.phanisment.itemcaster.menu.ability.buttons.EditSignalButton;
import io.phanisment.itemcaster.menu.ability.buttons.EditVariableButton;
import io.phanisment.itemcaster.menu.ability.buttons.ItemAbilityButton;

public class ItemAbilityButtons {
	private final List<ItemAbilityButton> BUTTONS = new ArrayList<>();

	public void reload() {
		new EditCooldownButton();
		new EditPowerButton();
		new EditShowCooldownButton();
		new EditSignalButton();
		new EditVariableButton();
	}

	public void add(ItemAbilityButton button) {
		BUTTONS.add(button);
	}

	public List<ItemAbilityButton> buttons() {
		return BUTTONS;
	}
}
