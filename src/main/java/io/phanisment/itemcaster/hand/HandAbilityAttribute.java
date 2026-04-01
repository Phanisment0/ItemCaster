package io.phanisment.itemcaster.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

import io.phanisment.itemcaster.skill.SkillAttribute;
import io.phanisment.itemcaster.util.Identifier;

public class HandAbilityAttribute {
	private final Identifier id;
	private final ConfigurationSection config;
	
	private String display;
	private List<SkillAttribute> abilities = new ArrayList<>();
	
	public HandAbilityAttribute(Identifier id, ConfigurationSection config) {
		this.id = id;
		this.config = config;
	}

	@SuppressWarnings("unchecked")
	public void load() {
		this.display = config.getString("display");
		
		for (Map<?, ?> map : config.getMapList("abilities")) abilities.add(new SkillAttribute((Map<String, Object>)map));
	}

	public Identifier getId() {
		return id;
	}

	public String getDisplay() {
		return display;
	}

	public List<SkillAttribute> getAttributes() {
		return abilities;
	}
}
