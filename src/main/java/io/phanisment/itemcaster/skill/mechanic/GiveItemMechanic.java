package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.phanisment.itemcaster.util.ItemUtil;

public class GiveItemMechanic implements ITargetedEntitySkill {
	private final PlaceholderString type;
	private final PlaceholderInt amount;

	public GiveItemMechanic(MythicLineConfig mlc) {
		this.type = mlc.getPlaceholderString(new String[] { "id", "i", "type", "t" }, "stone");
		this.amount = mlc.getPlaceholderInteger(new String[] { "amount", "a" }, 0);
	}

	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (target.getBukkitEntity() instanceof Player player) {
			ItemStack item = ItemUtil.getItem(type.get(target));
			if (!ItemUtil.validateItem(item)) return SkillResult.CONDITION_FAILED;
			int amount = this.amount.get(target);
			if (amount > 0) item.setAmount(amount);
			player.getInventory().addItem(item);
		}
		return SkillResult.CONDITION_FAILED;
	}
}