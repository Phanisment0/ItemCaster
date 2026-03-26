package io.phanisment.itemcaster.listener;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import io.phanisment.itemcaster.skill.Activator;
import io.phanisment.itemcaster.util.ItemUtil;

public class CasterRunnable extends BukkitRunnable {
	@Override
	public void run() {
		for (Player player : Bukkit.getOnlinePlayers()) if (player.isOnline() || !player.isDead()) ItemUtil.runSkill(player, Activator.TICK);
	}
}