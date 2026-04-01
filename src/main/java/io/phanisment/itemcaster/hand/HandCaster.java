package io.phanisment.itemcaster.hand;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.YamlConfiguration;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.parser.ValidateString;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Identifier;

public final class HandCaster {
	private static final Map<Identifier, HandAbilityAttribute> cache = new HashMap<>();
	public static final File FILE_LOCATION = new File(ItemCaster.inst().getDataFolder(), "hand");

	public static void load() {
		if (!FILE_LOCATION.exists()) {
			FILE_LOCATION.mkdirs();
			ItemCaster.inst().saveResource("hand/example.yml", false);
		}
		for (File file : FILE_LOCATION.listFiles()) {
			if (ValidateString.containsSpecial(file.getName())) {
				CasterLogger.send("<red>File name can't have special character/whitespace:</red> " + file.getName());
				continue;
			}

			var yml = YamlConfiguration.loadConfiguration(file);
			addAllAbility(yml, fileName(file.getName()));
		}
	}

	private static String fileName(String name) {
		int last_dot = name.lastIndexOf('.');
		if (last_dot == -1) return null;
		return name.substring(0, last_dot);
	}

	private static void addAllAbility(YamlConfiguration yml, String file_name) {
		for (String key : yml.getKeys(false)) {
			if (ValidateString.containsSpecial(key)) {
				CasterLogger.send("<red>Ability id can't have special character/whitespace</red>");
				continue;
			}

			var id = new Identifier(file_name, key);
			var skill = new HandAbilityAttribute(id, yml);
			skill.load();
			cache.put(id, skill);
		}
	}

	public static Map<Identifier, HandAbilityAttribute> getAbilities() {
		return cache;
	}

	public static HandAbilityAttribute getAbility(Identifier id) {
		return cache.get(id);
	}
}
