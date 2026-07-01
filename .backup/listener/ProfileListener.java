package io.phanisment.itemcaster.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import io.phanisment.itemcaster.profile.ProfileManager;

public class ProfileListener implements Listener {
	@EventHandler
	public void onJoin(PlayerJoinEvent e) {
		ProfileManager.load(e.getPlayer());
	}

	@EventHandler
	public void onLeave(PlayerQuitEvent e) {
		ProfileManager.remove(e.getPlayer());
	}
}