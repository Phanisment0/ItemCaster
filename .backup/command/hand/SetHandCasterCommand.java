package io.phanisment.itemcaster.command.hand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.hand.HandCaster;
import io.phanisment.itemcaster.profile.Profile;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Identifier;

public class SetHandCasterCommand extends Command<ItemCaster> {

	public SetHandCasterCommand(ItemCaster plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "set";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"s"};
  }

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.hand.set";
	}

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
public boolean onCommand(CommandSender sender, String[] args) {
		if (args == null) return true;

		Identifier id = new Identifier(args[0]);
		if (!HandCaster.getAbilities().containsKey(id)) {
			CasterLogger.send(sender, "<red>Unknown ability");
			return true;
		}

		Player target;
		if (args.length == 1) {
			if (!(sender instanceof Player player)) {
				CasterLogger.send(sender, "<red>Console must specify a player");
				return true;
			}

			target = player;
		} else {
			target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				CasterLogger.send(sender, "<red>Unknown player");
				return true;
			}
		}

		Profile profile = ProfileManager.get(target);
		profile.setAbility(id);
		CasterLogger.send(sender, "Set Hand Ability of <green>" + target.getName() + "</green> to <green>" + id + "</green>");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		List<String> list = new ArrayList<>();
		if (args.length == 1) for (Identifier id : HandCaster.getAbilities().keySet()) list.add(id.toString());
		else if (args.length == 2) for (Player player : Bukkit.getOnlinePlayers()) list.add(player.getName());
		return list;
	}
}
