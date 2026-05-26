package io.phanisment.itemcaster;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.profile.Profile;
import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public final class ItemCasterPlaceholderExpansion extends PlaceholderExpansion {
	@Override
	public String getAuthor() {
		return "Phanisment";
	}

	@Override
	public String getIdentifier() {
		return "itemcaster";
	}

	@Override
	public String getVersion() {
		return "0.1";
	}

	@Override
	public String onPlaceholderRequest(Player player, String params) {
		Profile profile = ProfileManager.get(player);
		ProfileData data = profile.getData();

		if (params.equalsIgnoreCase("handcaster_toggle")) return data.hand_toggle ? "true" : "false";
		else if (params.equalsIgnoreCase("handcaster_ability")) return data.hand_ability.toString();
		return null;
	}
}