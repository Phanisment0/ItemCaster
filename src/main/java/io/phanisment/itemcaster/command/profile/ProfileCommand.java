package io.phanisment.itemcaster.command.profile;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.util.CasterLogger;

public class ProfileCommand extends Command<ItemCaster> {

	public ProfileCommand(ItemCaster plugin) {
		super(plugin);
		this.addSubCommands(new ProfileSaveCommand(plugin));
	}

	@Override
	public String getName() {
		return "profile";
	}

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.profile";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"p"};
	}

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		CasterLogger.send(sender, "Command for Player data in ItemCaster");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}
}