package io.phanisment.itemcaster.skill.condition;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.skills.conditions.IEntityCondition;
import io.lumine.mythic.core.utils.annotations.MythicCondition;

@MythicCondition(author = "Phanisment", name = "isattackcooldown", aliases = { "attackcooldown", "atkcd" }, description = "If target attack is cooldown")
public class AttackCooldownCondition implements IEntityCondition {

	@Override
	public boolean check(AbstractEntity e) {
		Entity entity = e.getBukkitEntity();
		if (entity instanceof Player player) return player.getAttackCooldown() < 1.0;
		return false;
	}
}
