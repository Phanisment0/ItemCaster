package io.phanisment.itemcaster.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.Material;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersion;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersions;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;

import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.item.external.IExternalItem;

import java.util.Optional;
import java.util.Arrays;

public class ModelData {
	private Material type = Material.AIR;
	private int model_index = 0;
	private NamespacedKey item_model;
	private NamespacedKey tooltip_style;
	private CustomModelDataComponent model_data_component;
	
	private final MythicItem mi;
	private final MythicConfig config;
	
	public ModelData(String raw_item, MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();
		
		Optional<ItemStack> item = this.getItem(raw_item);
		if (!item.isPresent()) return;
		
		if (ServerVersion.isBefore(MinecraftVersion.parse("1.21.3"))) this.type = item.get().getType();
		
		ItemMeta meta = item.get().getItemMeta();
		if (meta != null) {
			if (meta.hasCustomModelData()) this.model_index = meta.getCustomModelData();
			if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.20.5")) && meta.hasTooltipStyle()) this.tooltip_style = meta.getTooltipStyle();
			if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.21.3")) && meta.hasItemModel()) this.item_model = meta.getItemModel();
			if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.21.4")) && meta.getCustomModelDataComponent() != null) this.model_data_component = meta.getCustomModelDataComponent();
		}
	}
	
	public Optional<ItemStack> getItem(String type) {
		if (type.contains(":")) {
			String[] parts = type.split(":");
			IExternalItem ei = ExternalItemRegistry.getRegistered(parts[0].toLowerCase());
			if (ei == null) {
				MythicLogger.errorItemConfig(mi, config, "Unknown External Item: " + parts[0].toLowerCase());
				return Optional.empty();
			}
			return ei.resolve(parts, mi);
		}
		
		try {
			return Optional.of(new ItemStack(Material.valueOf(type.toUpperCase())));
		} catch (IllegalArgumentException e) {
			MythicLogger.errorItemConfig(mi, config, "Unknown Material: " + type.toUpperCase());
			return Optional.empty();
		}
	}
	
	public boolean hasType() {
		return this.type != null && this.type != Material.AIR;
	}
	
	public boolean hasModelData() {
		return this.model_index > 0 ? true : false;
	}
	
	public boolean hasItemModel() {
		return this.item_model != null;
	}
	
	public boolean hasTooltipStyle() {
		return this.tooltip_style != null;
	}
	
	public boolean hasModelDataComponent() {
		return this.model_data_component != null;
	}
	
	public Material getType() {
		return this.type;
	}
	
	public int getModelData() {
		return this.model_index;
	}
	
	public NamespacedKey getItemModel() {
		return this.item_model;
	}
	
	public NamespacedKey getTooltipStyle() {
		return this.tooltip_style;
	}
	
	public CustomModelDataComponent getModelDataComponent() {
		return this.model_data_component;
	}
}