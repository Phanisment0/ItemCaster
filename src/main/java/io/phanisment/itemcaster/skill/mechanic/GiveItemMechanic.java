package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.adapters.AbstractPlayer;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.api.skills.ITargetedEntitySkill;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.api.skills.SkillResult;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import io.phanisment.itemcaster.util.ItemUtil;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author="Phanisment", name="itemcaster:giveitem", aliases={"itemcaster:give", "itemcaster:itemgive"}, description="Give target external item that support in ItemCaster")
public class GiveItemMechanic extends SkillMechanic implements ITargetedEntitySkill {
	private String type = "stone";
	private int amount = 0;
	
	public GiveItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		this.type = mlc.getString(new String[]{"id", "i", "type", "t"}, "stone");
		this.amount = mlc.getInteger(new String[]{"amount", "a"}, 0);
	}
	
	@Override
	public SkillResult castAtEntity(SkillMetadata meta, AbstractEntity target) {
		if (target.getBukkitEntity() instanceof Player player) {
			ItemStack item = ItemUtil.getItem(type);
			if (!ItemUtil.validateItem(item)) return SkillResult.CONDITION_FAILED;
			if (amount > 0) item.setAmount(amount);
			player.getInventory().addItem(item);
		}
		return SkillResult.CONDITION_FAILED;
	}
}