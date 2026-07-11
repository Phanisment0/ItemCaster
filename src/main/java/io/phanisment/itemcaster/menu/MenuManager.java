package io.phanisment.itemcaster.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;

import io.phanisment.itemcaster.hand.SlotActivator;
import io.phanisment.itemcaster.menu.ability.ItemAbilitiesMenu;
import io.phanisment.itemcaster.menu.ability.ItemAbilityButtons;
import io.phanisment.itemcaster.menu.ability.ItemAbilityMenu;
import io.phanisment.itemcaster.menu.ability.helper.ActivatorListMenu;
import io.phanisment.itemcaster.menu.ability.helper.SkillListMenu;
import io.phanisment.itemcaster.menu.ability.helper.ActivatorListMenu.ActivatorIcon;
import io.phanisment.itemcaster.menu.ability.variable.AbilityVariableListMenu;
import io.phanisment.itemcaster.menu.editor.ItemEditorButtons;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenu;
import io.phanisment.itemcaster.menu.editor.helper.MaterialListMenu;
import io.phanisment.itemcaster.menu.editor.helper.MaterialListMenu.MaterialIcon;
import io.phanisment.itemcaster.menu.item.ItemBrowseMenu;
import io.phanisment.itemcaster.skill.Activator;

public class MenuManager {
	public static final List<MaterialIcon> LIST_MATERIAL = new ArrayList<>();
	public static final MaterialListMenu MATERIAL_LIST_MENU = new MaterialListMenu();

	public static final List<ActivatorIcon> LIST_ACTIVATOR = new ArrayList<>();
	public static final ActivatorListMenu ACTIVATOR_LIST_MENU = new ActivatorListMenu();
	public static final SkillListMenu SKILL_LIST_MENU = new SkillListMenu();

	public static final ItemEditorButtons ITEM_EDITOR_BUTTONS = new ItemEditorButtons();
	public static final ItemAbilityButtons ITEM_ABILITY_BUTTONS = new ItemAbilityButtons();

	public static final MainMenu MAIN = new MainMenu();
	public static final ItemBrowseMenu ITEM_BROWSE = new ItemBrowseMenu();
	public static final ItemEditorMenu ITEM_EDITOR = new ItemEditorMenu();
	public static final ItemAbilitiesMenu ITEM_ABILITIES = new ItemAbilitiesMenu();
	public static final ItemAbilityMenu ITEM_ABILITY = new ItemAbilityMenu();
	public static final AbilityVariableListMenu ABILITY_VARIABLE_LIST_MENU = new AbilityVariableListMenu();

	public static void reload() {
		LIST_MATERIAL.clear();
		LIST_ACTIVATOR.clear();

		for (Material a : Material.values()) {
			if (a.isAir()) continue;
			if (!a.isItem()) continue;
			LIST_MATERIAL.add(new MaterialIcon(a.toString()));
		}
		MATERIAL_LIST_MENU.reload();

		for (Activator a : Activator.values()) LIST_ACTIVATOR.add(new ActivatorIcon(a.toString()));
		for (SlotActivator a : SlotActivator.values()) LIST_ACTIVATOR.add(new ActivatorIcon(a.toString()));
		ACTIVATOR_LIST_MENU.reload();
		SKILL_LIST_MENU.reload();

		MAIN.reload();
		ITEM_BROWSE.reload();
		ITEM_EDITOR.reload();
		ITEM_ABILITIES.reload();
		ITEM_ABILITY.reload();
		ABILITY_VARIABLE_LIST_MENU.reload();

		ITEM_EDITOR_BUTTONS.reload();
		ITEM_ABILITY_BUTTONS.reload();
	}
}