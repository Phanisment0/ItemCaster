package io.phanisment.itemcaster.hand;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.skill.Activator;
import io.phanisment.itemcaster.skill.SkillActivator;

public class HandActivator {
  private final Player player;
  private final Activator activator;
  private final NamespacedKey ability;
  
  public HandActivator(Player player, Activator activator) {
    this.player = player;
    this.activator = activator;
    this.ability = ProfileManager.get(player).DATA.hand_ability;
  }

  public void execute() {
    HandAbilityAttribute abilities = HandCaster.getAbility(ability);
    new SkillActivator(player, activator).setAttributes(abilities.getAttributes()).execute();
  }
}
