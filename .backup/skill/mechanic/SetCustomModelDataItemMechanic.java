package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderBoolean;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.phanisment.itemcaster.util.ItemUtil;
import io.phanisment.itemcaster.item.ModelData;
import io.phanisment.itemcaster.skill.template.ItemMechanic;

import java.util.Optional;

public class SetCustomModelDataItemMechanic extends ItemMechanic {
	private PlaceholderInt data;
	private PlaceholderBoolean external;
	private PlaceholderString type;
	
	public SetCustomModelDataItemMechanic(MythicLineConfig mlc) {
		super(mlc);
		this.data = mlc.getPlaceholderInteger(new String[]{"model", "m", "data", "d"}, 0);
		this.external = mlc.getPlaceholderBoolean(new String[]{"external", "ex"}, false);
		this.type = mlc.getPlaceholderString(new String[]{"type", "t", "id", "i"}, "stone");
	}
	
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!ItemUtil.validateItem(item)) return Optional.empty();
		ItemMeta meta = item.getItemMeta();
		if (external.get(target)) {
			var model_data = new ModelData(type.get(target).toLowerCase());
			model_data.applyModel(item);
			item.setItemMeta(meta);
			return Optional.of(item);
		}
		meta.setCustomModelData(data.get(target));
		item.setItemMeta(meta);
		return Optional.of(item);
	}
}