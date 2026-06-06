package io.phanisment.itemcaster;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.phanisment.itemcaster.hand.HandAbilityAttribute;
import io.phanisment.itemcaster.hand.HandCaster;
import io.phanisment.itemcaster.parser.DataPathParser;
import io.phanisment.itemcaster.profile.Profile;
import io.phanisment.itemcaster.profile.ProfileData;
import io.phanisment.itemcaster.profile.ProfileManager;
import io.phanisment.itemcaster.util.ItemUtil;
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

	/**
	 * This is still in development, and this is crazy flexible lol but im really lazy to test it.
	 * 
	 * If you want example, you can just get all data in SkillAttribute instanly without me add all of it manually.
	 */
	@Override
	public String onPlaceholderRequest(Player player, String params) {
		ItemStack item = player.getInventory().getItemInMainHand();
		Profile profile = ProfileManager.get(player);
		ProfileData data = profile.getData();

		if (params.equalsIgnoreCase("mythic_item")) return ItemUtil.isMythicItem(item) ? "true" : "false";
		else if (params.equalsIgnoreCase("hand_toggle")) return data.hand_toggle ? "true" : "false";
		else if (params.equals("hand_ability")) return data.hand_ability.toString();
		if (params.startsWith("hand_abilities.")) {
			int dot_loc = params.indexOf('.');
			if (dot_loc == -1 || dot_loc == params.length() - 1) return "";
			String path = params.substring(dot_loc + 1);
			
			HandAbilityAttribute attribute = HandCaster.getAbility(data.hand_ability);
			if (attribute == null || attribute.getAttributes() == null) return "";
			Object result = DataPathParser.parse(attribute.getAttributes(), path);
			return result != null ? String.valueOf(result) : "";
		}

		return null;
	}
}