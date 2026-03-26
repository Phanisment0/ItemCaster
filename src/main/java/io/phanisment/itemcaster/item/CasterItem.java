package io.phanisment.itemcaster.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.api.adapters.AbstractItemStackRarity;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.skills.placeholders.parsers.PlaceholderStringImpl;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersion;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.Storage;
import io.phanisment.itemcaster.util.ItemAbilityUtil;
import io.phanisment.itemcaster.skill.SkillAttribute;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

/**
 * MythicItem Wrapper
 */
public class CasterItem {
	public static final NamespacedKey TYPE = new NamespacedKey(ItemCaster.core(), "type");
	private static final Map<String, CasterItem> items = new HashMap<>();
	private final MythicItem mi;
	private final MythicConfig config;

	private boolean hide_tooltips = false;
	private ModelData model_data;
	private List<SkillAttribute> abilities = new ArrayList<>();

	@SuppressWarnings("unchecked")
	public CasterItem(MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();

		this.hide_tooltips = config.getBoolean("Options.HideTooltips");
		this.model_data = new ModelData(config.getString("ModelItem"), mi);

		List<SkillAttribute> new_list_ability = new ArrayList<>();
    for (Map<?, ?> map : config.getMapList("Abilities")) new_list_ability.add(new SkillAttribute((Map<String, Object>)map));
    this.abilities = new_list_ability;

		items.put(mi.getInternalName(), this);
	}

	/**
	 * Apply configuration data to MythicItem ItemStack.
	 * 
	 * @param e Mythicmobs item generation event.
	 */
	public void applyData(MythicMobItemGenerateEvent e) {
		ItemStack item = e.getItemStack();
		ItemMeta meta = item.getItemMeta();

		if (Storage.hasResourcePack() && model_data != null) model_data.applyModel(item);
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.20.5")) && hide_tooltips) meta.setHideTooltip(true);

		item.setItemMeta(meta);

		if (abilities != null && !abilities.isEmpty()) item = this.parseAbilities(item);

		e.setItemStack(item);
	}

	private ItemStack parseAbilities(ItemStack item) {
		for (SkillAttribute attribute : abilities) {
			if (attribute.skill != null || attribute.activator != null) {
				MythicLogger.errorItemConfig(mi, config, "Required attributes `skill` and `activator` in Abilities component!");
				continue;
			}

			item = ItemAbilityUtil.of(item).addAbility(attribute).orElse(item);
		}
		return item;
	}

	// Has ///////////////////////////////////////
	public boolean hasModelData() {
		return model_data.hasItemModel();
	}

	public boolean hasAbilities() {
		return abilities != null;
	}

	// Add /////////////////////////////////////
	public void addAbility(SkillAttribute attribute) {
		abilities.add(attribute);
		save("Abilities", abilities);
	}

	// Is //////////////////////////////////////
	public boolean isUnbreakable() {
		return this.mi.isUnbreakable();
	}

	public boolean isPreventAnvil() {
		return mi.getPreventAnvilWith();
	}

	// Set /////////////////////////////////////
	
	/*public void setAttributes() {
		save(, TYPE);
	}*/

	public void setMaxDurability(int value) {
		save("MaxDurability", value);
	}

	public void setDurability(int value) {
		save("Durability", value);
	}

	public void setRarity(AbstractItemStackRarity value) {
		save("Rarity", value.toString());
	}

	public void setTemplate(String value) {
		save("Template", value);
	}

	public void setPreventAnvil(boolean value) {
		save("Options.PreventAnvil", value);
	}

	public void setHideTooltips(boolean hide) {
		hide_tooltips = hide;
		save("Options.HideTooltips", hide_tooltips);
	}

	public void setModelData(String model) {
		if (model != null) model_data = new ModelData(model, mi);
		else model_data = null;
		save("ModelItem", model_data);
	}

	public void setLore(List<String> lore) {
		List<PlaceholderString> list = new ArrayList<>();
		for (var line : lore) list.add(new PlaceholderStringImpl(line));
		mi.setLore(list);
		save("Lore", lore);
	}

	public void setAbility(int index, SkillAttribute attribute) {
		abilities.set(index, attribute);
		save("Abilities", abilities);
	}

	public void setUnbreakable(boolean unbreakable) {
		save("Options.Unbreakable", unbreakable);
	}

	public void setName(String name) {
		save("Display", name);
	}

	public void setModel(int id) {
		save("Model", id);
	}

	// Remove /////////////////////////////
	public void removeMaxDurability() {
		save("MaxDurability", null);
	}

	public void removeDurability() {
		save("Durability", null);
	}

	public void removeRarity() {
		save("Rarity", null);
	}
	
	public void removeTemplate() {
		save("Template", null);
	}

	public void removeAbility(int index) {
		abilities.remove(index);
		save("Abilities", abilities);
	}

	public void removeAbilities() {
		abilities.clear();
		save("Abilities", null);
	}

	public void removeName() {
		save("Display", null);
	}

	public void removeModelData() {
		model_data = null;
		save("ModelItem", null);
	}

	public void removeHideTooltips() {
		hide_tooltips = false;
		save("HideTooltips", null);
	}

	public void removeLore(int index) {
		List<PlaceholderString> list = mi.getLoreRaw();
		list.remove(index);
		save("Lore", list);
	}

	public void removeLores() {
		save("Lore", null);
	}

	public void removeModel() {
		save("Model", null);
	}

	public void removeUnbreakable() {
		save("Unbreakable", null);
	}

	public void removePreventAnvil() {
		save("Options.PreventAnvil", null);
	}

	// Get /////////////////////////////////
	public String getId() {
		return this.mi.getInternalName();
	}

	public List<String> getLore() {
		return this.mi.getLore();
	}

	public MythicConfig getConfig() {
		return this.config;
	}

	public boolean getHideTooltips() {
		return this.hide_tooltips;
	}

	public ModelData getModelData() {
		return this.model_data;
	}

	public SkillAttribute getAbility(int index) {
		return this.abilities.get(index);
	}

	public List<SkillAttribute> getAbilities() {
		return this.abilities;
	}

	public MythicItem getItem() {
		return this.mi;
	}

	@Deprecated
	public ItemStack getItemCached() {
		return this.mi.getCachedBaseItem();
	}

	public ItemStack getItemStack() {
		return this.getItemStack(1);
	}

	public ItemStack getItemStack(int amount) {
		return BukkitAdapter.adapt(this.mi.generateItemStack(amount));
	}

	public static Optional<CasterItem> getCasterItem(MythicItem item) {
		return getCasterItem(item.getInternalName());
	}

	public static Optional<CasterItem> getCasterItem(String name) {
		return Optional.of(items.get(name));
	}

	/**
	 * Convert ItemStack that has data type of Mythicmobs
	 * 
	 * @param convert Item that want to convert.
	 * @return instance of registered CasterItem.
	 */
	public static Optional<CasterItem> fromItemStack(ItemStack convert) {
		if (!validateItem(convert)) return Optional.empty();
		ItemMeta meta = convert.getItemMeta();
		if (meta == null) return Optional.empty();
		String name = meta.getPersistentDataContainer().get(TYPE, PersistentDataType.STRING);
		if (name == null || name.isBlank()) return Optional.empty();
		return getCasterItem(name);
	}

	public static void clear() {
		items.clear();
	}

	private void save(String key, Object value) {
		config.setSave(key, value);
		mi.loadItem();
		mi.buildItemCache();
	}
}