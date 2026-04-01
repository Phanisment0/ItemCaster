package io.phanisment.itemcaster.command.hand;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;

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
	public boolean onCommand(CommandSender arg0, String[] arg1) {
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}
	
}
