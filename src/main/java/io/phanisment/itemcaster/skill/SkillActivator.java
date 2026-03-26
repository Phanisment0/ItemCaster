package io.phanisment.itemcaster.skill;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import de.tr7zw.nbtapi.NBT;
import de.tr7zw.nbtapi.iface.ReadWriteNBT;
import de.tr7zw.nbtapi.iface.ReadableNBTList;

import io.lumine.mythic.api.skills.Skill;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.MythicMobsUtil;

import java.util.Map;
import java.util.Optional;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

public class SkillActivator {
	private static final String ITEM_CASTER = "ItemCaster";
	private static final String ABILITIIES = "abilities";
	private static final Map<UUID, Map<String, Integer>> skill_interval = new HashMap<>();

	private final Player player;
	private final IActivator activator;
	
	private List<SkillAttribute> attributes = new ArrayList<>();
	private String signal = null;
	
	public boolean CANCEL_EVENT = false;

	public SkillActivator(Player player, IActivator activator) {
		this.player = player;
		this.activator = activator;
	}

	public SkillActivator(Player player, IActivator activator, ItemStack item) {
		this(player, activator);

		if (!validateItem(item)) return;
		ReadWriteNBT inst = NBT.itemStackToNBT(item).getCompound(ITEM_CASTER);
		this.setAttributes(inst.getCompoundList(ABILITIIES));
	}

	public void execute() {
		for (SkillAttribute attribute : attributes) {
			String skill = attribute.skill;
			String event = attribute.activator;

			if (event.equalsIgnoreCase(activator.toString()) && !skill.isEmpty()) {
				CasterLogger.info("Event is {0} and skill {1} is not empty", event, skill);
				CasterLogger.info("{0} is equal {1}", event, activator);
				
				Optional<Skill> mybe_skill = MythicMobsUtil.toSkill(skill);
				if (!mybe_skill.isPresent()) continue;
				
				if (attribute.cancel_event != null) this.CANCEL_EVENT = attribute.cancel_event; 
				if (mybe_skill.get().onCooldown(MythicMobsUtil.toCaster(player))) continue;
				if (attribute.sneaking && player.isSneaking()) continue;
				if (event.equals("SIGNAL") && signal.equals(attribute.signal)) continue;
				if (event.equals("TICK") && !this.canCastOnInterval(player.getUniqueId(), attribute)) continue;

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

	public SkillActivator setAttributes(ReadableNBTList<ReadWriteNBT> data_list) {
		for (ReadWriteNBT data : data_list) attributes.add(new SkillAttribute(data));
		return this;
	}

	private boolean canCastOnInterval(UUID uuid, SkillAttribute attribute) {
		Map<String, Integer> skill_in_interval = skill_interval.computeIfAbsent(uuid, k -> new HashMap<>());
		int count = skill_in_interval.getOrDefault(attribute.skill, 0);
		if (count > 0) {
			skill_in_interval.put(attribute.skill, count - 1);
			skill_interval.put(uuid, skill_in_interval);
			return false;
		} else {
			skill_in_interval.remove(attribute.skill);
			skill_interval.put(uuid, skill_in_interval);
		}
		skill_in_interval.put(attribute.skill, attribute.interval);
		skill_interval.put(uuid, skill_in_interval);
		return true;
	}
}