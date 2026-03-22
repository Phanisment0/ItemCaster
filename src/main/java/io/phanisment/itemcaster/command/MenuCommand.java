package io.phanisment.itemcaster.command;

import java.util.List;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.commands.Command;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.menu.MenuBrowser;

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
  public boolean isConsoleFriendly() {
    return false;
  }

  @Override
  public boolean onCommand(CommandSender sender, String[] args) {
    new MenuBrowser().open((Player)sender);
    return true;
  }

  @Override
  public List<String> onTabComplete(CommandSender arg0, String[] arg1) {
    return null;
  }

}
