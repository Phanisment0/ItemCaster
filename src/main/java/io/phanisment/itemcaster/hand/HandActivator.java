package io.phanisment.itemcaster.hand;

import java.util.Optional;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.skill.IActivator;
import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Identifier;

public class HandActivator {
	private final Player player;
	private final IActivator activator;
	private Identifier ability_id = null;
	private String signal = null;
	
	public HandActivator(Player player, IActivator activator) {
		this.player = player;
		this.activator = activator;
		Optional<ProfileData> data = ProfileManager.get(player).getData();
		if (data.isPresent()) ability_id = data.get().hand_ability;
	}

	public HandActivator setSignal(String signal) {
		this.signal = signal;
		return this;
	}

	public void execute() {
		//CasterLogger.send(player, "Ability Id:" + ability_id + " Activator:" + activator);
		if (ability_id == null) return;
		HandAbilityAttribute abilities = HandCaster.getAbility(ability_id);
		var exc = new SkillActivator(player, activator);
		if (signal != null) exc.setSignal(signal);
		exc.setAttributes(abilities.getAttributes()).execute();
	}
}
