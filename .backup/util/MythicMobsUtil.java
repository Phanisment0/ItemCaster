package io.phanisment.itemcaster.util;

import java.util.Optional;

import org.bukkit.entity.Entity;

import io.lumine.mythic.api.adapters.AbstractLocation;
import io.lumine.mythic.api.mobs.GenericCaster;
import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.api.skills.SkillCaster;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.adapters.VirtualEntity;
import io.lumine.mythic.core.players.PlayerData;
import io.phanisment.itemcaster.ItemCaster;

public final class MythicMobsUtil {
	public static SkillCaster toCaster(Entity target) {
		if (target == null) {
			var virtual_entity = new VirtualEntity(new AbstractLocation());
			return new GenericCaster(virtual_entity);
		}

		if (ItemCaster.core().getMobManager().isActiveMob(target.getUniqueId())) return ItemCaster.core().getMobManager().getMythicMobInstance(target);

		Optional<PlayerData> player_data = ItemCaster.core().getPlayerManager().getProfile(target.getUniqueId());
		if (player_data.isPresent()) return player_data.get();
		return new GenericCaster(BukkitAdapter.adapt(target));
	}

	public static Optional<Skill> toSkill(String skill) {
		return ItemCaster.core().getSkillManager().getSkill(skill);
	}
}
