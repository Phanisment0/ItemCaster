package io.phanisment.itemcaster.item;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersion;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;

import io.phanisment.itemcaster.Constants;
import io.phanisment.itemcaster.util.MapSafe;
import io.phanisment.itemcaster.util.ItemAbilityUtil;
import io.phanisment.itemcaster.util.ItemAbilityUtil.AbilityData;

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
		this.model_data = new ModelData(config.getString("ModelItem"), mi);
		this.abilities = (List<Map<String, Object>>)(Object)config.getMapList("Abilities");
	}
	
	public void applyData(MythicMobItemGenerateEvent e) {
		ItemStack item = e.getItemStack();
		ItemMeta meta = item.getItemMeta();
		
		if (Constants.hasResourcePack()) applyModel(model_data, item);
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.20.5")) && hide_tooltip) meta.setHideTooltip(true);
		
		item.setItemMeta(meta);
		
		if (abilities != null && !abilities.isEmpty()) item = this.parseAbilities(item);
		
		e.setItemStack(item);
	}
	
	private ItemStack parseAbilities(ItemStack item) {
		for (Map<String, Object> ability : abilities) {
			var safe = new MapSafe(ability);
			if (!ability.containsKey("skill") || !ability.containsKey("activator")) {
				MythicLogger.errorItemConfig(mi, config, "Required attributes `skill` and `activator` in Abilities component!");
				continue;
			}
			
			AbilityData data = new AbilityData(safe.getString("skill"),safe.getString("activator"));
			if (ability.containsKey("power")) data.setPower(safe.getFloat("power"));
			if (ability.containsKey("cooldown")) data.setCooldown(safe.getDouble("cooldown"));
			if (ability.containsKey("interval")) data.setInterval(safe.getInteger("interval"));
			if (ability.containsKey("sneaking")) data.setSneaking(safe.getBoolean("sneaking"));
			if (ability.containsKey("signal")) data.setSignal(safe.getString("signal"));
			if (ability.containsKey("variables")) data.setVariables(safe.getMap("variables"));
			item = ItemAbilityUtil.of(item).addAbility(data).orElse(item);
		}
		return item;
	}
	
	@SuppressWarnings("deprecation")
	public static void applyModel(ModelData model_data, ItemStack item) {
		if (model_data.hasModelData()) item.getItemMeta().setCustomModelData(model_data.getModelData());
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.20.5")) && model_data.hasTooltipStyle()) item.getItemMeta().setTooltipStyle(model_data.getTooltipStyle());
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.21.3")) && model_data.hasItemModel()) item.getItemMeta().setItemModel(model_data.getItemModel());
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.21.4")) && model_data.hasModelDataComponent()) item.getItemMeta().setCustomModelDataComponent(model_data.getModelDataComponent());
		if (ServerVersion.isBefore(MinecraftVersion.parse("1.21.4")) && model_data.hasType()) item.setType(model_data.getType());
	}
	
	public MythicItem getItem() {
		return this.mi;
	}
}