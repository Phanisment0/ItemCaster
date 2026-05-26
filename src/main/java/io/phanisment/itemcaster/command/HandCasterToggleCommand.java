package io.phanisment.itemcaster.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.command.Command;

import io.phanisment.itemcaster.profile.Profile;
import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.CasterLogger;

public class HandCasterToggleCommand implements TabExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Profile profile = ProfileManager.get((Player)sender);
		ProfileData data = profile.getData();
		data.hand_toggle = !data.hand_toggle;
		profile.save();

		if (args.length == 1 && args[0] == "off") return true;
		CasterLogger.send(sender, "Toggled Hand Activator to: " + (data.hand_toggle ? "<green>True" : "<red>False"));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
		List<String> result = new ArrayList<>();
		if (args.length == 1) {
			result.add("off");
			result.add("<Type `off` to disable message>");
		}
		return null;
	}
}
