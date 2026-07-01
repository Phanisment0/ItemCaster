package io.phanisment.itemcaster.command.hand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.profile.Profile;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.CasterLogger;

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
		Player target;
		if (args == null) {
			if (!(sender instanceof Player player)) {
				CasterLogger.send(sender, "Console must specify a player");
				return true;
			}
			target = player;
		} else {
			target = Bukkit.getPlayer(args[0]);
			if (target == null) {
				CasterLogger.send(sender, "Unknown player");
				return true;
			}
		}

		Profile profile = ProfileManager.get(target);
		profile.setAbility(null);
		CasterLogger.send(sender, "Removed Hand Ability of <green>" + target.getName() + "</green>");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());
		return list;
	}
}
