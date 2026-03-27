package io.phanisment.itemcaster.skill;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBTCompoundList;

import io.lumine.mythic.api.skills.Skill;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.MythicMobsUtil;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

public class SkillActivator {
	private final Player player;
	private final IActivator activator;
	
	private List<SkillAttribute> attributes = new ArrayList<>();
	private String signal = null;
	
	public boolean CANCEL_EVENT = false;

	public SkillActivator(Player player, IActivator activator) {
		this.player = player;
		this.activator = activator;
	}

	@SuppressWarnings("deprecation")
	public SkillActivator(Player player, IActivator activator, ItemStack item) {
		this(player, activator);

		if (!validateItem(item)) return;
		ReadWriteNBT inst = new NBTItem(item).getCompound("ItemCaster");
		if (inst == null) return;
		this.setAttributes(inst.getCompoundList("abilities"));
	}

	public void execute() {
		CasterLogger.info("Executing skills for player {0}, activator: {1}, total attributes: {2}", player.getName(), activator.toString(), attributes.size());
		for (SkillAttribute attribute : attributes) {
			String skill = attribute.skill;
			String event = attribute.activator;
			CasterLogger.info("Processing attribute - Skill: {0}, Event: {1}, Signal: {2}", skill, event, attribute.signal);

			if (event.equalsIgnoreCase(activator.toString()) && !skill.isEmpty()) {
				CasterLogger.info("Event is {0} and skill {1} is not empty", event, skill);
				CasterLogger.info("{0} is equal {1}", event, activator);
				
				Optional<Skill> mybe_skill = MythicMobsUtil.toSkill(skill);
				if (!mybe_skill.isPresent()) continue;
				
				if (attribute.cancel_event != null) this.CANCEL_EVENT = attribute.cancel_event; 
				if (mybe_skill.get().onCooldown(MythicMobsUtil.toCaster(player))) continue;
				if ((attribute.sneaking != null ? attribute.sneaking : false) && player.isSneaking()) continue;
				if (event.equals("SIGNAL") && signal.equals(attribute.signal)) continue;

				new SkillExecutor(mybe_skill.get(), player).setAttribute(attribute).execute();
			}
		}
	}

	public SkillActivator setSignal(String signal) {
		this.signal = signal;
		return this;
	}

	public SkillActivator setAttributes(List<SkillAttribute> data_list) {
		this.attributes = data_list;
		return this;
	}

	public SkillActivator setAttributes(ReadWriteNBTCompoundList data_list) {
		for (ReadWriteNBT data : data_list) attributes.add(new SkillAttribute(data));
		return this;
	}
}