package io.phanisment.itemcaster.config;

public class ConfigData {
	public static transient ConfigData handler = new ConfigData();
	public boolean debug_mode = false;
	public String prefix = "<gradient:#69DFFF:#5984CF>ItemCaster</gradient> | ";
	public boolean auto_set_skill_cooldown = true;
	public String language = "en_us";

	public static ConfigData getInst() {
		return handler;
	}
}