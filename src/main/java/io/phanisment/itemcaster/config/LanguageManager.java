package io.phanisment.itemcaster.config;

import java.io.File;
import java.io.IOException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import io.phanisment.itemcaster.ItemCaster;

public class LanguageManager {
	public static final Gson gson = new GsonBuilder().create();
	public static String default_language = "lang/en_us.json";
	public static JsonObject lang_json;
	public static File lang_location;

	public LanguageManager() {
		var folder = new File(ItemCaster.inst().getDataFolder(), "lang");
		if (!folder.exists()) {
			folder.mkdirs();
			ItemCaster.inst().saveResource(default_language, false);
		}
	}

	public void load() {
		String lang_code = ConfigData.handler.language;
		lang_location = new File(ItemCaster.inst().getDataFolder(), "lang/" + lang_code + ".json");
		if (!lang_location.exists()) lang_location = new File(ItemCaster.inst().getDataFolder(), default_language);
		try (var reader = new java.io.FileReader(lang_location)) {
			lang_json = gson.fromJson(reader, JsonObject.class);
		} catch (JsonSyntaxException | IOException e) {
			e.printStackTrace();
		}
	}

	public static String getString(String key) {
		if (lang_json == null || !lang_json.has(key)) return key;
		return lang_json.get(key).getAsString();
	}

	public static File getLangFile() {
		return lang_location;
	}
}