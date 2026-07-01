package io.phanisment.itemcaster.listener;

import org.bukkit.scheduler.BukkitRunnable;

import io.phanisment.itemcaster.profile.ProfileManager;

public class ProfileRunnable extends BukkitRunnable {

	@Override
	public void run() {
		ProfileManager.saveAll();
	}

}
