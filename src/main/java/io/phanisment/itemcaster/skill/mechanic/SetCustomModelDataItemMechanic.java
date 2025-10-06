package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import io.phanisment.itemcaster.util.ItemUtil;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.item.ModelData;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author="Phanisment", name="setcustommodeldataitem", description="Set the custom model data item", aliases={
	"itemcaster:setcustommodeldataitem",
	"itemcaster:setmodeldataitem",
	"itemcaster:modeldataitem",
	"setmodeldataitem",
	"modeldataitem"
})
public class SetCustomModelDataItemMechanic extends ItemMechanic {
	private int data;
	private boolean external;
	private String type;
	
	public SetCustomModelDataItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		this.data = mlc.getInteger(new String[]{"model", "m", "data", "d"}, 0);
		this.external = mlc.getBoolean(new String[]{"external", "ex"}, false);
		this.type = mlc.getString(new String[]{"type", "t", "id", "i"}, "stone");
	}
	
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!ItemUtil.validateItem(item)) return Optional.empty();
		ItemMeta meta = item.getItemMeta();
		if (external) {
			var model_data = new ModelData(this.type, null);
			CasterItem.applyModel(model_data, item);
			item.setItemMeta(meta);
			return Optional.of(item);
		}
		meta.setCustomModelData(data);
		item.setItemMeta(meta);
		return Optional.of(item);
	}
}