package io.phanisment.itemcaster.hand;

import java.util.Optional;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.bukkit.utils.lib.jooq.Meta;
import io.lumine.mythic.core.skills.MetaSkill;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.skill.SkillAttribute;
import io.phanisment.itemcaster.skill.SkillExecutor;

public class HandActivator {
  private final Player player;
  private final NamespacedKey ability;
  
  public HandActivator(Player player) {
    this.player = player;
    this.ability = ProfileManager.get(player).DATA.hand_ability;
  }

  public void execute() {
    HandAbilityAttribute abilities = HandCaster.getAbility(ability);
    for (SkillAttribute skill_attribute : abilities.getAttributes()) {
      Optional<Skill> skill = ItemCaster.getSkillManager().getSkill(skill_attribute.getSkill());
      if (!skill.isPresent()) continue;

      var exc = new SkillExecutor((MetaSkill)skill.get(), player);
      exc.setCooldown(skill_attribute.getCooldown());
      exc.setPower(skill_attribute.getPower());
      //exc.setVariables(skill_attribute.getVariables());
    }
  }
}
