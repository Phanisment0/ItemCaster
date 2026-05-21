package io.phanisment.itemcaster.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import io.lumine.mythic.core.skills.SkillCondition;
import io.lumine.mythic.core.skills.conditions.InvalidCondition;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.skill.SkillAttribute;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Identifier;

public class HandAbilityAttribute {
	private final Identifier id;
	private final ConfigurationSection config;
	
	private String display;
	private List<SkillCondition> conditions = new ArrayList<>();
	private List<SkillAttribute> abilities = new ArrayList<>();
	
	public HandAbilityAttribute(final Identifier id, final ConfigurationSection config) {
		this.id = id;
		this.config = config;
	}

	@SuppressWarnings("unchecked")
	public void load() {
		this.display = config.getString("display");

		List<String> string_conditions = config.getStringList("conditions");
		for (int i = 0; i < string_conditions.size(); i++) {
			SkillCondition sc = ItemCaster.core().getSkillManager().getCondition(string_conditions.get(i));

			if (sc != null && !(sc instanceof InvalidCondition)) conditions.add(sc);
			else CasterLogger.send("<red>Invalid Condition");
		}
		for (Map<?, ?> map : config.getMapList("abilities")) abilities.add(new SkillAttribute((Map<String, Object>)map));
	}

	public Identifier getId() {
		return id;
	}

	public String getDisplay() {
		return display;
	}

	public List<SkillCondition> getConditions() {
		return conditions;
	}

	public List<SkillAttribute> getAttributes() {
		return abilities;
	}

	@Override
	public String toString() {
		return "HandAbilityAttribute(id=" + id + ", display=" + display + ", abilites=" + abilities + ")";
	}
}