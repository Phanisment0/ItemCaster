package io.phanisment.itemcaster.command;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.util.CasterLogger;

public class ItemCasterRelaodCommand extends Command<ItemCaster> {

	public ItemCasterRelaodCommand(ItemCaster plugin) {
		super(plugin);
	}

	@Override
	public String getName() {
		return "reload";
	}

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.reload";
	}

	@Override
	public String[] getAliases() {
		return new String[]{"r"};
	}

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		CasterLogger.send(sender, "<red>Use mythicmobs reload command to reload this plugin.</red> `/mm r`");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}
}
