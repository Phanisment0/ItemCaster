package io.phanisment.itemcaster.hand;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.bukkit.NamespacedKey;
import org.bukkit.configuration.ConfigurationSection;

import io.phanisment.itemcaster.skill.SkillAttribute;

public class HandAbilityAttribute {
  private final NamespacedKey id;
  private final ConfigurationSection config;
  
  private String display;
  private List<SkillAttribute> abilities = new ArrayList<>();
  
  public HandAbilityAttribute(NamespacedKey id, ConfigurationSection config) {
    this.id = id;
    this.config = config;
  }

  @SuppressWarnings("unchecked")
  public void load() {
    this.display = config.getString("display");
    
    List<SkillAttribute> new_list_ability = new ArrayList<>();
    for (Map<?, ?> map : config.getMapList("abilities")) new_list_ability.add(new SkillAttribute((Map<String, Object>)map));
    this.abilities = new_list_ability;
  }

  public NamespacedKey getId() {
    return id;
  }

  public String getDisplay() {
    return display;
  }

  public List<SkillAttribute> getAttributes() {
    return abilities;
  }
}
