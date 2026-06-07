package io.phanisment.itemcaster.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.bukkit.Material;

import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersion;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;

import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.item.external.IExternalItem;

import java.util.Optional;

/**
 * Handles the model data for a CasterItem, including custom model data, item models,
 * tooltip styles, and armor models.
 */
public class ModelData {
	private Material type = Material.AIR;
	private int model_index = 0;
	private NamespacedKey item_model;
	private NamespacedKey tooltip_style;
	private NamespacedKey armor_model;
	private CustomModelDataComponent model_data_component;

	private final MythicItem mi;
	private final MythicConfig config;

	public ModelData(String item_type) {
		this(item_type, null);
	}

	public ModelData(String item_type, MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();

		Optional<ItemStack> item = this.getItem(item_type);
		if (!item.isPresent()) return;

		this.type = item.get().getType();

		ItemMeta meta = item.get().getItemMeta();
		if (meta == null) return;
		
		if (meta.hasCustomModelData()) this.model_index = meta.getCustomModelData();
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.20.5")) && meta.hasTooltipStyle()) this.tooltip_style = meta.getTooltipStyle();
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.21.3"))) {
			if (meta.hasItemModel()) this.item_model = meta.getItemModel();
			EquippableComponent armor_model_component = meta.getEquippable();
			if (armor_model_component != null) this.armor_model = armor_model_component.getModel();
		}
	}

	@SuppressWarnings("deprecation")
	public void applyModel(ItemStack item) {
		if (item == null) return;

		if (hasType()) item.setType(type);
		if (hasModelData()) mi.setCustomModelData(model_index);

		ItemMeta meta = item.getItemMeta();
		if (meta == null) return;
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.20.5")) && hasTooltipStyle()) meta.setTooltipStyle(this.tooltip_style);

		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.21.3"))) {
			// if (hasItemModel()) meta.setItemModel(this.item_model);
			if (hasArmorModel()) {
				EquippableComponent equippable = meta.getEquippable();
				if (equippable != null) {
					equippable.setModel(this.armor_model);
					meta.setEquippable(equippable);
				}
			}
		}
	}
	
	public Optional<ItemStack> getItem(String type) {
		if (type == null || type.isBlank()) return Optional.empty();
		if (type.contains(":")) {
			String[] parts = type.split(":");
			IExternalItem ei = ExternalItemRegistry.getRegistered(parts[0].toLowerCase());
			if (ei == null) {
				if (config != null) MythicLogger.errorItemConfig(mi, config, "Unknown External Item: " + parts[0].toLowerCase());
				return Optional.empty();
			}
			return ei.resolve(parts, mi);
		}

		try {
			return Optional.of(new ItemStack(Material.valueOf(type.toUpperCase())));
		} catch (IllegalArgumentException e) {
			if (config != null)
				MythicLogger.errorItemConfig(mi, config, "Unknown Material: " + type.toUpperCase());
			return Optional.empty();
		}
	}

	public boolean isEmpty() {
		return hasType() || hasModelData();
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

	public boolean hasArmorModel() {
		return this.armor_model != null;
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

	public NamespacedKey getArmorModel() {
		return this.armor_model;
	}

	public CustomModelDataComponent getModelDataComponent() {
		return this.model_data_component;
	}
}