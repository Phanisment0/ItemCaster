package io.phanisment.itemcaster.menu.ability.helper;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.ability.ItemAbilityMenuContext;
import io.phanisment.itemcaster.skill.SkillAttribute;

public class SkillListMenuContext {
	private final CasterItem item;
	private final ItemAbilityMenuContext previous_context;
	private final SkillAttribute attribute;
	private final int index;

	private String filter = null;

	public SkillListMenuContext(CasterItem item, ItemAbilityMenuContext previous_context, SkillAttribute attribute, int index) {
		this.item = item;
		this.previous_context = previous_context;
		this.attribute = attribute;
		this.index = index;
	}

	public void filter(String value) {
		this.filter = value;
	}

	public String filter() {
		return filter;
	}

	public CasterItem item() {
		return item;
	}

	public SkillAttribute attribute() {
		return attribute;
	}

	public int index() {
		return index;
	}

	public void open(Player player) {
		MenuManager.SKILL_LIST_MENU.open(player, this);
	}

	public void openPrevious(Player player) {
		MenuManager.ITEM_ABILITY.open(player, previous_context);
	}
}
