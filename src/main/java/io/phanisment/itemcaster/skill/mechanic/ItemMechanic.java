package io.phanisment.itemcaster.skill.mechanic;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMechanic;
import java.io.File;

public class ItemMechanic extends SkillMechanic {
  public ItemMechanic(SkillExecutor manager, File file, String skill, MythicLineConfig config) {
    super(manager, file, skill, config);
    
  }
}
