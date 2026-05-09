package io.phanisment.itemcaster.command.profile;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.profile.ProfileManager;

public class ProfileSaveAllCommand extends Command<ItemCaster> {

	public ProfileSaveAllCommand(ItemCaster plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "saveall";
	}

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.profile.saveall";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"sa", "sall"};
	}

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		ProfileManager.saveAll();
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}
}