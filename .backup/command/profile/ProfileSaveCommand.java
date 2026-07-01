package io.phanisment.itemcaster.command.profile;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.CasterLogger;

public class ProfileSaveCommand extends Command<ItemCaster> {

	public ProfileSaveCommand(ItemCaster plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "save";
	}

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.profile.save";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"s"};
	}

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		if (args.length > 0) {
			OfflinePlayer off_player = Bukkit.getOfflinePlayer(args[0]);
			if (off_player.isOnline()) {
				ProfileManager.get((Player)off_player).save();
				CasterLogger.send(sender, "<green>Target data is saved");
			} else CasterLogger.send(sender, "<red>Target is offline");
		} else {
			ProfileManager.get((Player)sender).save();
			CasterLogger.send(sender, "<green>Your data is saved");
		}
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}
}