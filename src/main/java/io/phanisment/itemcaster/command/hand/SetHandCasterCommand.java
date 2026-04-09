package io.phanisment.itemcaster.command.hand;

import java.util.ArrayList;
import java.util.List;

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
		if (args.length > 1) {
			CasterLogger.send(sender, "<red>Can't contains whitespace");
			return true;
		}
		Profile profile = ProfileManager.get((Player)sender);
		if (profile.getData().isPresent()) profile.setAbility(new Identifier(args[1]));
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		List<String> list = new ArrayList<>();
		for (Identifier id : HandCaster.getAbilities().keySet()) list.add(id.toString());
		return list;
	}
}
