package io.phanisment.itemcaster.command.hand;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.util.CasterLogger;

public class HandCasterCommand extends Command<ItemCaster> {
	public HandCasterCommand(ItemCaster plugin) {
		super(plugin);
		this.addSubCommands(new SetHandCasterCommand(plugin));
	}

	@Override
	public String getName() {
		return "hand";
	}

	@Override
  public String[] getAliases() {
    return new String[]{"h"};
  }

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.hand";
	}

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		CasterLogger.send((Player)sender, "Command for HandCaster");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, String[] args) {
		return null;
	}	
}
