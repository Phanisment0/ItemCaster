package io.phanisment.itemcaster.command;

import java.util.List;
import java.util.Optional;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.profile.Profile;
import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.CasterLogger;

public class HandCasterToggleCommand extends Command<ItemCaster> {

	public HandCasterToggleCommand(ItemCaster plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "menu";
	}

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.handcastertoggle";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"hct"};
	}

	@Override
	public boolean isConsoleFriendly() {
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		Profile profile = ProfileManager.get((Player)sender);
		Optional<ProfileData> data = profile.getData();
		if (data.isPresent()) data.get().hand_toggle = !data.get().hand_toggle;
		profile.save();
		CasterLogger.send(sender, "Toggled Hand Activator to: " + (data.get().hand_toggle ? "<green>True" : "<red>False"));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}

}
