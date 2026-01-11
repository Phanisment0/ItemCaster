package io.phanisment.itemcaster.config;

public class ConfigData {
	public static transient ConfigData handler = new ConfigData();
	public String language = "en_us";
//public static boolean enable_recipe_configuration = false;

	public static ConfigData getInst() {
		return handler;
	}
}