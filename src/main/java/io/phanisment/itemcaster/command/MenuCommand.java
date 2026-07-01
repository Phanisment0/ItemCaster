package io.phanisment.itemcaster.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.menu.MenuManager;

public class MenuCommand extends Command<ItemCaster> {

	public MenuCommand(ItemCaster plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "menu";
	}

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.menu";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"m"};
	}

	@Override
	public boolean isConsoleFriendly() {
		return false;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		var player = (Player)sender;
		MenuManager.MAIN.open(player, player);
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}

}
