package io.phanisment.itemcaster.skill;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.utils.jnbt.CompoundTag;
import io.lumine.mythic.core.utils.jnbt.ListTag;
import io.lumine.mythic.core.utils.jnbt.Tag;
import io.phanisment.itemcaster.skill.SkillAttribute.AttributeKeys;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.MythicMobsUtil;

import java.util.Optional;

import javax.annotation.Nonnull;

import java.util.ArrayList;
import java.util.List;

public class SkillActivator {
	public static final String INSTANE = "ItemCaster";
	public static final String ABILITIES = "abilities";

	private final Player player;
	private final IActivator activator;
	
	private List<SkillAttribute> attributes = new ArrayList<>();
	private String signal = null;
	
	public boolean CANCEL_EVENT = false;

	public SkillActivator(Player player, IActivator activator) {
		this.player = player;
		this.activator = activator;
	}

	public SkillActivator(Player player, IActivator activator, @Nonnull ItemStack item) {
		this(player, activator);
		CompoundTag tc = MythicBukkit.inst().getVolatileCodeHandler().getItemHandler().getNBTData(item);
		if (tc == null) return;
		if (!tc.containsKey(INSTANE)) return;
		CompoundTag inst = tc.getCompound(INSTANE);
		if (inst == null) return;
		if (!inst.containsKey(ABILITIES)) return;
		this.setAttributes(inst.getListTag(ABILITIES));
	}

	public void execute() {
		CasterLogger.info("Executing skills for player {0}, activator: {1}, total attributes: {2}", player.getName(), activator.toString(), attributes.size());
		for (SkillAttribute attribute : attributes) {
			String skill = attribute.get(AttributeKeys.SKILL, String.class, "");
			String event = attribute.get(AttributeKeys.ACTIVATOR, String.class, "");
			CasterLogger.info("Processing attribute - Skill: {0}, Event: {1}, Signal: {2}", skill, event, attribute.get(AttributeKeys.SIGNAL, String.class, ""));

			if (event.equalsIgnoreCase(activator.toString()) && !skill.isEmpty()) {
				CasterLogger.info("Event is {0} and skill {1} is not empty", event, skill);
				CasterLogger.info("{0} is equal {1}", event, activator);
				
				Optional<Skill> mybe_skill = MythicMobsUtil.toSkill(skill);
				if (!mybe_skill.isPresent()) continue;
				
				this.CANCEL_EVENT = attribute.get(AttributeKeys.CANCEL_EVENT, Boolean.class, false); 
				if (event.equals("SIGNAL") && signal.equals(attribute.get(AttributeKeys.SIGNAL, String.class, ""))) continue;

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

	public SkillActivator setAttributes(ListTag data_list) {
		for (Tag data : data_list.getValue()) attributes.add(new SkillAttribute((CompoundTag)data));
		return this;
	}
}