package io.phanisment.itemcaster.command;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;

public class HandCasterToggle extends Command<ItemCaster> {

	public HandCasterToggle(ItemCaster plugin) {
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
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}

}
