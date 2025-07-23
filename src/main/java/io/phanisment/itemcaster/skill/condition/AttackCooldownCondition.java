package io.phanisment.itemcaster.skill.condition;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.logging.MythicLogger.DebugLevel;

public class AttackCooldownCondition implements IEntityCondition {
  @Override
  public boolean check(AbstractEntity t) {
    Entity entity = t.getBukkitEntity();
    if (entity instanceof Player player) {
      MythicLogger.debug(DebugLevel.CONDITION, "Player {0} attack cooldown {1}", player.getName(), player.getAttackCooldown() < 1.0);
      return player.getAttackCooldown() < 1.0;
    }
    return false;
  }
}
