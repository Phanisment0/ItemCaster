package io.phanisment.itemcaster;

import org.bukkit.entity.Player;
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
		return null;
	}
}