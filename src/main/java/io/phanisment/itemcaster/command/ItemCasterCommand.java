package io.phanisment.itemcaster.command;

import java.util.List;

import org.bukkit.command.CommandSender;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.util.CasterLogger;

public class ItemCasterCommand extends Command<ItemCaster> {

	public ItemCasterCommand(ItemCaster plugin) {
		super(plugin);
		this.addSubCommands(new MenuCommand(plugin));
		// this.addSubCommands(new ReloadCommand());
	}

	@Override
	public String getName() {
		return null;
	}

	@Override
	public String getPermissionNode() {
		return "itemcaster.command.main";
	}

	@Override
  public String[] getAliases() {
    return new String[]{"ic"};
  }

	@Override
	public boolean isConsoleFriendly() {
		return true;
	}

	@Override
	public boolean onCommand(CommandSender sender, String[] args) {
		CasterLogger.send(sender, "Main command for ItemCaster");
		return true;
	}

	@Override
	public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
		return null;
	}
}