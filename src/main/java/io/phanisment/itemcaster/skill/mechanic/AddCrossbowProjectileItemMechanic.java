package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.CrossbowMeta;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;
import io.lumine.mythic.core.skills.SkillExecutor;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.io.File;
import java.util.Optional;

@MythicMechanic(author="Phanisment", name="addcrossbowprojectileitem", aliases={"crossbowprojectile"}, description="Add item for projectile in crossbow")
public class AddCrossbowProjectileItemMechanic extends ItemMechanic {
	private Material type;
	
	public AddCrossbowProjectileItemMechanic(SkillExecutor manager, File file, String line, MythicLineConfig mlc) {
		super(manager, file, line, mlc);
		try {
			this.type = Material.valueOf(mlc.getString(new String[]{"material", "m", "type", "t"}, "ARROW").toUpperCase());
		} catch (IllegalArgumentException e) {
			this.type = Material.ARROW;
		}
	}
	
	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		ItemMeta meta = item.getItemMeta();
		if (meta instanceof CrossbowMeta cs) {
			cs.addChargedProjectile(new ItemStack(this.type));
			item.setItemMeta(cs);
			return Optional.of(item);
		}
		return Optional.empty();
	}
}