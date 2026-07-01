package io.phanisment.itemcaster.skill;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.core.utils.jnbt.CompoundTag;
import io.lumine.mythic.core.utils.jnbt.ListTag;
import io.lumine.mythic.core.utils.jnbt.Tag;
import io.phanisment.itemcaster.skill.SkillAttribute.Key;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.MythicMobsUtil;

import java.util.Optional;
import java.util.ArrayList;
import java.util.List;

import static io.phanisment.itemcaster.ItemCaster.core;
import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

public class SkillActivator {
	private static final Byte ZERO = 0;
	public static final String INSTANCE = "ItemCaster";
	public static final String ABILITIES = "abilities";

	private final Player player;
	private final IActivator activator;
	
	private List<SkillAttribute> attributes = new ArrayList<>();
	private String signal = null;
	
	public byte CANCEL_EVENT = 0;

	public SkillActivator(Player player, IActivator activator) {
		this.player = player;
		this.activator = activator;
	}

	public SkillActivator(Player player, IActivator activator, ItemStack item) {
		this(player, activator);

		if (!validateItem(item)) return;
		CompoundTag inst = core().getVolatileCodeHandler().getItemHandler().getNBTData(item).getCompound(INSTANCE);
		if (inst == null) return;
		this.setAttributes(inst.getListTag(ABILITIES));
	}

	public void execute() {
		CasterLogger.info("Executing skills for player {0}, activator: {1}, total attributes: {2}", player.getName(), activator.toString(), attributes.size());
		for (SkillAttribute attribute : attributes) {
			String skill = attribute.get(Key.SKILL, String.class, "");
			String event = attribute.get(Key.ACTIVATOR, String.class, "");
			CasterLogger.info("Processing attribute - Skill: {0}, Event: {1}, Signal: {2}", skill, event, attribute.get(Key.SIGNAL, String.class, ""));

			if (event.equalsIgnoreCase(activator.toString()) && !skill.isEmpty()) {
				CasterLogger.info("Event is {0} and skill {1} is not empty", event, skill);
				CasterLogger.info("{0} is equal {1}", event, activator);
				
				Optional<Skill> mybe_skill = MythicMobsUtil.toSkill(skill);
				if (!mybe_skill.isPresent()) continue;
				
				this.CANCEL_EVENT = attribute.get(Key.CANCEL_EVENT, Byte.class, ZERO); 
				if (event.equals("SIGNAL") && signal.equals(attribute.get(Key.SIGNAL, String.class, null))) continue;

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

	public SkillActivator setAttributes(AbilitiesContainer data_list) {
		this.attributes = data_list;
		return this;
	}

	public SkillActivator setAttributes(ListTag data_list) {
		for (Tag data : data_list.getValue()) attributes.add(new SkillAttribute((CompoundTag)data));
		return this;
	}
}