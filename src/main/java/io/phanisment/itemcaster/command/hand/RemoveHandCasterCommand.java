package io.phanisment.itemcaster.command.hand;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.profile.Profile;
import io.phanisment.itemcaster.profile.ProfileManager;

public class RemoveHandCasterCommand extends Command<ItemCaster> {

	public RemoveHandCasterCommand(ItemCaster plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "remove";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"r"};
  }

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.hand.remove";
	}

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		Profile profile = ProfileManager.get((Player)sender);
		if (profile.getData().isPresent()) profile.setAbility(null);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}
}
