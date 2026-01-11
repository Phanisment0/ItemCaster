package io.phanisment.itemcaster.menu;

import java.util.Optional;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.menu.editor.ability.AbilityMenu;
import io.phanisment.itemcaster.menu.editor.ability.button.IAbilityButton;
import io.phanisment.itemcaster.menu.editor.ability.button.IntervalAbilityButton;
import io.phanisment.itemcaster.menu.editor.ability.button.PowerAbilityButton;
import io.phanisment.itemcaster.menu.editor.ability.button.SignalAbilityButton;
import io.phanisment.itemcaster.menu.editor.ability.button.SkillAbilityButton;
import io.phanisment.itemcaster.menu.editor.ability.button.SneakingEditorButton;
import io.phanisment.itemcaster.menu.editor.ability.button.VariablesEditorButton;
import io.phanisment.itemcaster.menu.editor.ability.button.ActivatorAbilityButton;
import io.phanisment.itemcaster.menu.editor.ability.button.CooldownAbilityButton;
import io.phanisment.itemcaster.menu.editor.button.AbilitiesEditorButton;
import io.phanisment.itemcaster.menu.editor.button.DisplayNameEditorButton;
import io.phanisment.itemcaster.menu.editor.button.IEditorButton;
import io.phanisment.itemcaster.menu.editor.button.ModelItemEditorButton;
import io.phanisment.itemcaster.menu.editor.button.PreventAnvilEditorButton;
import io.phanisment.itemcaster.menu.editor.button.MaterialEditorButton;
import io.phanisment.itemcaster.menu.editor.button.ModelEditorButton;
import io.phanisment.itemcaster.menu.editor.button.UnbreakableEditorButton;

public class MenuManager {
	public MenuManager() {
		// Menu editor
		registerEditorButton(new MaterialEditorButton());
		registerEditorButton(new DisplayNameEditorButton());
		registerEditorButton(new ModelEditorButton());
		registerEditorButton(new ModelItemEditorButton());
		registerEditorButton(new UnbreakableEditorButton());
		registerEditorButton(new AbilitiesEditorButton());
		registerEditorButton(new PreventAnvilEditorButton());

		// Ability Editor
		registerAbilityButton(new SkillAbilityButton());
		registerAbilityButton(new ActivatorAbilityButton());
		registerAbilityButton(new CooldownAbilityButton());
		registerAbilityButton(new PowerAbilityButton());
		registerAbilityButton(new IntervalAbilityButton());
		registerAbilityButton(new SneakingEditorButton());
		registerAbilityButton(new SignalAbilityButton());
		registerAbilityButton(new VariablesEditorButton());
	}

	public static void registerEditorButton(IEditorButton button) {
		MenuEditor.buttons.add(button);
	}

	public static void registerAbilityButton(IAbilityButton button) {
		AbilityMenu.buttons.add(button);
	}

	public static void openBrowser(Player player) {
		new MenuBrowser().open(player);
	}
	
	public static void openEditor(Player player, Optional<CasterItem> item) {
		new MenuEditor(item).open(player);
	}
}
