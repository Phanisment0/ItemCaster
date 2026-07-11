package io.phanisment.itemcaster.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;

import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.skills.placeholders.parsers.PlaceholderStringImpl;
import io.lumine.mythic.core.utils.jnbt.CompoundTag;
import io.lumine.mythic.core.utils.jnbt.CompoundTagBuilder;
import io.lumine.mythic.core.utils.jnbt.ListTag;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.menu.MenuData;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersion;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.Storage;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.item.ItemBrowseMenuContext;
import io.phanisment.itemcaster.skill.SkillActivator;
import io.phanisment.itemcaster.skill.SkillAttribute;
import io.phanisment.itemcaster.skill.SkillAttribute.AttributeKeys;
import io.phanisment.itemcaster.util.CasterColorCode;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.Optional;

import static io.phanisment.itemcaster.ItemCaster.core;
import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

/**
 * MythicItem Wrapper
 */
public class CasterItem implements MenuData<ItemBrowseMenuContext> {
	public static final NamespacedKey TYPE = new NamespacedKey(ItemCaster.core(), "type");
	public static final Map<String, CasterItem> ITEMS = new HashMap<>();
	private final MythicItem mi;
	private final MythicConfig config;

	private boolean hide_tooltips = false;
	private ModelData model_data;
	private List<SkillAttribute> abilities = new ArrayList<>();

	public CasterItem(MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();

		this.hide_tooltips = config.getBoolean("Options.HideTooltips");
		this.model_data = new ModelData(config.getString("ModelItem"), mi);

		try {
			var list = config.getMapList("Abilities");
			for (Map<?, ?> attr : list) this.abilities.add(new SkillAttribute((Map<String, Object>)attr));
		} catch (Exception e) {
			e.printStackTrace();
		}

		ITEMS.put(mi.getInternalName(), this);
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

	@Override
	public Icon<ItemBrowseMenuContext> getIcon() {
		return IconBuilder.<ItemBrowseMenuContext>create().itemStack(this.getItemStack()).hideFlags().name("<white>" + getId()).lore(List.of(
			"",
			CasterColorCode.GREEN + "✉ <gray>L - Get the Item",
			CasterColorCode.ORANGE + "✎ <gray>R - Edit the Item",
			CasterColorCode.MAGENTA + "☈ <gray>S+R - " + CasterColorCode.RED + "Remove the Item"
		)).click((context, player) -> {
			CasterMenu.playMenuClick(player);
			player.getInventory().addItem(this.getItemStack());
		}).rightClick((context, player) -> {
			CasterMenu.playMenuClick(player);
			MenuManager.ITEM_EDITOR.open(player, this);
		}).shiftRightClick((ctx, p) -> {
			this.delete();
			ctx.open(p);
		}).build();
	}

	private ItemStack parseAbilities(ItemStack item) {
		if (abilities.isEmpty()) return item;

		CompoundTagBuilder builder = core().getVolatileCodeHandler().getItemHandler().getNBTData(item).createBuilder();
		List<CompoundTag> tags = new ArrayList<>();

		for (SkillAttribute attribute : abilities) {
			if (!attribute.has(AttributeKeys.SKILL) || !attribute.has(AttributeKeys.ACTIVATOR)) {
				MythicLogger.errorItemConfig(mi, config, "Required attributes 'skill' and 'activator' in Abilities component!");
				continue;
			}
			tags.add(attribute.getAsCompound());
		}

		if (tags.isEmpty()) return item;
		CompoundTag nbt = builder.put(SkillActivator.INSTANE, CompoundTagBuilder.create().put(SkillActivator.ABILITIES, new ListTag(CompoundTag.class, tags)).build()).build();

		return core().getVolatileCodeHandler().getItemHandler().setNBTData(item, nbt);
	}

	// Has ///////////////////////////////////////
	public boolean hasModelData() {
		return model_data.hasItemModel();
	}

	public boolean hasAbilities() {
		return !abilities.isEmpty();
	}

	// Add /////////////////////////////////////
	public void addAbility(SkillAttribute attribute) {
		abilities.add(attribute);
		List<Map<String, Object>> raw = new ArrayList<>();
		for (var attr : abilities) raw.add(attr.getAsMap());
		save("Abilities", raw);
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

	public void setGlint(boolean value) {
		save("Options.Glint", value);
	}

	public void setFireResistance(boolean value) {
		save("Options.FireResistant", value);
	}

	public void setTrim(String value) {
		save("Trim", value);
	}

	public void setMaxDurability(int value) {
		save("MaxDurability", value);
	}

	public void setMaxStack(int value) {
		save("Options.StackSize", value);
	}

	public void setDurability(int value) {
		save("Durability", value);
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
		save("ModelItem", model);
	}

	public void setLore(List<String> lore) {
		List<PlaceholderString> list = new ArrayList<>();
		for (var line : lore) list.add(new PlaceholderStringImpl(line));
		mi.setLore(list);
		save("Lore", lore);
	}

	public void setAbility(int index, SkillAttribute attribute) {
		abilities.set(index, attribute);
		List<Map<String, Object>> raw = new ArrayList<>();
		for (var attr : abilities) raw.add(attr.getAsMap());
		save("Abilities", raw);
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

	public void setCustomModelData(int data) {
		save("CustomModelData", data);
	}

	public void setGroup(String group) {
		save("Group", group);
	}

	// Remove /////////////////////////////

	public void removeGlint() {
		save("Options.Glint", null);
	}

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
		if (abilities.isEmpty()) removeAbilities();
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
		List<String> list = mi.getLore();
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

	public void removeMaxStack() {
		save("Options.StackSize", null);
	}

	public void removeGroup() {
		save("Group", null);
	}

	public void removeTrim() {
		save("Trim", null);
	}

	public void removeFireResistance() {
		save("Options.FireResistant", null);
	}

	// Get /////////////////////////////////

	public boolean getGlint() {
		return mi.getEnchantGlint() == null ? false : mi.getEnchantGlint();
	}

	public boolean getFireResistance() {
		return mi.getFireResistant() == null ? false : mi.getFireResistant();
	}

	public String getId() {
		return this.mi.getInternalName();
	}

	public int getMaxStack() {
		return mi.getMaxStackSize();
	}

	public int getDurability() {
		return mi.getDurability().get();
	}

	public int getMaxDurability() {
		return mi.getMaxDurability().get();
	}

	public List<String> getLore() {
		return this.mi.getLore();
	}

	public MythicConfig getConfig() {
		return this.config;
	}

	public String getTrim() {
		return mi.getArmorTrim() == null ? null : mi.getArmorTrim().get();
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

	public int getCustomModelData() {
		return this.mi.getCustomModelData();
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
		return Optional.of(ITEMS.get(name));
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

	public void delete() {
		core().getItemManager().deleteItem(mi);
		ITEMS.remove(mi.getInternalName());
	}

	public static void clear() {
		ITEMS.clear();
	}

	private void save(String key, Object value) {
		config.setSave(key, value);
		mi.loadItem();
		mi.buildItemCache();
	}
}