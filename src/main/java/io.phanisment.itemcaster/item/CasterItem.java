package io.phanisment.itemcaster.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersion;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersions;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;

import de.tr7zw.nbtapi.NBTItem;
import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTCompoundList;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class CasterItem {
	private final MythicItem mi;
	private final MythicConfig config;
	
	private boolean hide_tooltip = false;
	private ModelData model_data;
	private List<Map<String, Object>> abilities = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public CasterItem(MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();
		
		this.hide_tooltip = config.getBoolean("Options.HideTooltip");
		this.model_data = new ModelData(config.getString("Model"), mi);
		this.abilities = (List<Map<String, Object>>)(Object)config.getMapList("Abilities");
	}
	
	@SuppressWarnings("deprecation")
	public void applyData(MythicMobItemGenerateEvent e) {
		ItemStack item = e.getItemStack();
		ItemMeta meta = item.getItemMeta();
		
		if (!model_data.isEmpty()) {
			item.setType(model_data.getType());
			meta.setCustomModelData(model_data.getModel());
		}
		
		if (ServerVersion.isAfter(MinecraftVersion.parse("1.21"))) {
			if (hide_tooltip) meta.setHideTooltip(true);
		}
		
		item.setItemMeta(meta);
		
		NBTItem nbt_item = new NBTItem(item);
		NBTCompound nbt = nbt_item.getOrCreateCompound("ItemCaster");
		
		if (abilities != null) this.parseAbilities(abilities, nbt);
		item = nbt_item.getItem();
		
		e.setItemStack(item);
	}
	
	@SuppressWarnings("unchecked")
	public void parseAbilities(List<Map<String, Object>> data, NBTCompound nbt) {
		NBTCompoundList abilitiesList = nbt.getCompoundList("abilities");
		for (Map<String, Object> ability : abilities) {
			
			if (!ability.containsKey("skill") || !ability.containsKey("activator")) {
				MythicLogger.errorItemConfig(mi, config, "Required attributes `skill` and `activator` in Abilities component!");
				continue;
			}
			
			NBTCompound skillCompound = abilitiesList.addCompound();
			skillCompound.setString("skill", (String)ability.get("skill"));
			skillCompound.setString("activator", (String)ability.get("activator"));
			
			if (ability.containsKey("power")) skillCompound.setFloat("power", getSafeFloat(ability, "power"));
			if (ability.containsKey("cooldown")) skillCompound.setDouble("cooldown", getSafeDouble(ability, "cooldown"));
			if (ability.containsKey("signal")) skillCompound.setString("signal", getSafeString(ability, "signal"));
			if (ability.containsKey("interval")) skillCompound.setInteger("interval", getSafeInteger(ability, "interval"));
			if (ability.containsKey("sneaking")) skillCompound.setBoolean("sneaking", getSafeBoolean(ability, "sneaking"));
			if (ability.containsKey("cancel_event")) skillCompound.setBoolean("cancel_event", getSafeBoolean(ability, "cancel_event"));
			
			if (ability.containsKey("variables")) {
				Map<String, Object> variables = (Map<String, Object>)ability.get("variables");
				NBTCompound variableCompound = skillCompound.getOrCreateCompound("variables");
				for (Map.Entry<String, Object> entry : variables.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					
					if (value instanceof String) {
						variableCompound.setString(key, (String)value);
					} else if (value instanceof Number) {
						if (value instanceof Float || value instanceof Double) {
							variableCompound.setFloat(key, ((Number)value).floatValue());
						} else {
							variableCompound.setInteger(key, ((Number)value).intValue());
						}
					} else if (value instanceof Boolean) {
						variableCompound.setBoolean(key, (Boolean)value);
					} else {
						variableCompound.setString(key, String.valueOf(value));
					}
				}
			}
		}
	}
	
	private static String getSafeString(Map<String, Object> map, String key) {
		Object value = map.get(key);
		return value instanceof String ? (String) value : String.valueOf(value);
	}
	
	private static float getSafeFloat(Map<String, Object> map, String key) {
		Object value = map.get(key);
		if (value instanceof Number) {
			return ((Number)value).floatValue();
		} else if (value instanceof String) {
			try {
				return Float.parseFloat((String) value);
			} catch (NumberFormatException e) {
				
			}
		}
		return 0f;
	}
	
	private static double getSafeDouble(Map<String, Object> map, String key) {
		Object value = map.get(key);
		if (value instanceof Number) {
			return ((Number)value).doubleValue();
		} else if (value instanceof String) {
			try {
				return Double.parseDouble((String) value);
			} catch (NumberFormatException e) {
				
			}
		}
		return 0D;
	}
	
	private static int getSafeInteger(Map<String, Object> map, String key) {
		Object value = map.get(key);
		if (value instanceof Number) {
			return ((Number) value).intValue();
		} else if (value instanceof String) {
			try {
				return Integer.parseInt((String) value);
			} catch (NumberFormatException e) {
				
			}
		}
		return 0;
	}
	
	private static boolean getSafeBoolean(Map<String, Object> map, String key) {
		Object value = map.get(key);
		if (value instanceof Boolean) {
			return (Boolean) value;
		} else if (value instanceof String) {
			return Boolean.parseBoolean((String) value);
		}
		return false;
	}
	
	public MythicItem getItem() {
		return this.mi;
	}
}