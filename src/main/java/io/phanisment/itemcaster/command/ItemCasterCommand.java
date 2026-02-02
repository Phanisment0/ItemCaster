package io.phanisment.itemcaster.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.phanisment.itemcaster.menu.MenuBrowser;

public class ItemCasterCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		switch (args[0]) {
			case "menu": 
			new MenuBrowser().open((Player) sender);
			break;
			default:
				break;
		}
		return true;
	}
}