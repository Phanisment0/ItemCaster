package io.phanisment.itemcaster.skill.mechanic;

import java.io.File;

import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.SkillMetadata;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillMechanic;

public abstract class ItemMechanic extends SkillMechanic {
	private EquipmentSlot slot;
	public ItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		String raw_slot = mlc.getString(new String[]{"slot", "s"}).toUpperCase();
		try {
			this.slot = EquipmentSlot.valueOf(raw_slot);
		} catch (EnumConstantNotPresentException e) {
			MythicLogger.errorMechanicConfig(this, mlc, "Unkown Slot type: " + raw_slot);
		}
	}
	@Override
	public boolean execute(SkillMetadata meta) {
		Player player = BukkitAdapter.adapt(meta.getCaster().getEntity().asPlayer());
		ItemStack item = this.getItem();
		if (item != null) {
			player.getInventory().setItem(slot, item);
			return true;
		}
		return false;
	}

	abstract ItemStack getItem();
}