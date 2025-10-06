package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author="Phanisment", name="settypeitem", aliases={"setitemtype", "itemtype"}, description="Change only the material/type item")
public class SetTypeItemMechanic extends ItemMechanic {
	private Material type = Material.STONE;
	
	public SetTypeItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		String type_string = mlc.getString(new String[]{"material", "m", "type", "t"}, "STONE").toUpperCase();
		try {
			this.type = Material.valueOf(type_string);
		} catch (IllegalArgumentException e) {
			MythicLogger.errorMechanic(this, "Invalid material: " + type_string);
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		item.setType(this.type);
		return Optional.of(item);
	}
}