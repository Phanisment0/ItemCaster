package io.phanisment.itemcaster.listener;

import org.bukkit.scheduler.BukkitRunnable;

import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.CasterLogger;

public class ProfileRunnable extends BukkitRunnable {

	@Override
	public void run() {
		ProfileManager.saveAll();
		CasterLogger.send("Players data saved");
	}

}
