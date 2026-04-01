package io.phanisment.itemcaster.profile;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public final class ProfileManager {
	private static final Map<UUID, Profile> profile_cache = new HashMap<>();

	public static Profile load(Player player) {
		var profile = new Profile(player);
		profile.load();
		return profile_cache.put(player.getUniqueId(), profile);
	}

	public static Profile get(Player player) {
		var profile = get(player.getUniqueId());
		if (!player.isOnline()) return getOffline(player);
		return profile == null ? load(player) : profile;
	}

	public static Profile get(UUID uuid) {
		return profile_cache.get(uuid);
	}

	public static Profile getOffline(OfflinePlayer player) {
		return getOffline(player.getUniqueId());
	}

	public static Profile getOffline(UUID uuid) {
		var profile = new Profile(Bukkit.getOfflinePlayer(uuid));
		profile.load();
		return profile;
	}

	public static Profile remove(Player player) {
		return remove(player.getUniqueId());
	}

	public static Profile remove(UUID uuid) {
		get(uuid).save();
		return profile_cache.remove(uuid);
	}

	public static void loadAll() {
		Bukkit.getOnlinePlayers().forEach(player -> get(player).load());	
	}

	public static void saveAll() {
		Bukkit.getOnlinePlayers().forEach(player -> get(player).save());
	}
}
