package io.phanisment.itemcaster.item;

import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.bukkit.events.MythicMobItemGenerateEvent;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.bukkit.utils.version.MinecraftVersion;
import io.lumine.mythic.bukkit.utils.version.ServerVersion;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.Constants;
import io.phanisment.itemcaster.util.MapSafe;
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
	private List<Map<String, Object>> abilities = new ArrayList<>();
	
	@SuppressWarnings("unchecked")
	public CasterItem(MythicItem mi) {
		this.mi = mi;
		this.config = mi.getConfig();
		
		this.hide_tooltips = config.getBoolean("Options.HideTooltips");
		this.model_data = new ModelData(config.getString("ModelItem"), mi);
		this.abilities = (List<Map<String, Object>>)(Object)config.getMapList("Abilities");
		
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
		
		if (Constants.hasResourcePack() && model_data != null) model_data.applyModel(item);
		if (ServerVersion.isAfterOrEq(MinecraftVersion.parse("1.20.5")) && hide_tooltips) meta.setHideTooltip(true);
		
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
			
			var data = new SkillAttribute(safe.getString("skill"), safe.getString("activator"));
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
	
	public boolean hasModelData() {
		return model_data != null;
	}
	
	public boolean hasAbilities() {
		return abilities != null;
	}
	
	public void addAbility(SkillAttribute attribute) {
		abilities.add(attribute.toMap());
		config.setSave("Abilities", abilities);
		mi.loadItem();
		mi.buildItemCache();
	}
	
	public void setHideTooltips(boolean hide) {
		hide_tooltips = hide;
		config.setSave("Options.HideTooltips", hide_tooltips);
		mi.loadItem();
		mi.buildItemCache();
	}
	
	public void setModelData(String model) {
		model_data = new ModelData(model, mi);
		config.setSave("ModelItem", model_data);
		mi.loadItem();
		mi.buildItemCache();
	}
	
	public void setAbility(int index, SkillAttribute attribute) {
		abilities.set(index, attribute.toMap());
		config.setSave("Abilities", abilities);
		mi.loadItem();
		mi.buildItemCache();
	}
	
	public void removeAbility(int index) {
		abilities.remove(index);
		config.setSave("Abilities", abilities);
		mi.loadItem();
		mi.buildItemCache();
	}
	
	public String getName() {
		return this.mi.getInternalName();
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
		return SkillAttribute.fromMap(this.abilities.get(index));
	}
	
	public List<Map<String, Object>> getAbilities() {
		return this.abilities;
	}
	
	public MythicItem getItem() {
		return this.mi;
	}
	
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
	 * @return        instance of registered CasterItem.
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
}