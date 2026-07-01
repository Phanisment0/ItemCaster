package io.phanisment.itemcaster.skill.mechanic;

import org.bukkit.EntityEffect;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.Damageable;

import io.lumine.mythic.api.adapters.AbstractEntity;
import io.lumine.mythic.api.config.MythicLineConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.phanisment.itemcaster.skill.template.ItemMechanic;

import static io.phanisment.itemcaster.util.ItemUtil.validateItem;

import java.util.Optional;

public class DamageItemMechanic extends ItemMechanic {
	private final PlaceholderInt amount;

	public DamageItemMechanic(MythicLineConfig mlc) {
		super(mlc);
		this.amount = mlc.getPlaceholderInteger(new String[] { "amount", "a" }, 1);
	}

	@Override
	public Optional<ItemStack> resolve(AbstractEntity target, ItemStack item) {
		if (!validateItem(item)) return Optional.empty();
		
		ItemMeta meta = item.getItemMeta();
		if (meta == null) return Optional.empty();
		if (meta instanceof Damageable dmg) {
			int mx_damage = item.getType().getMaxDurability(); 
			if (mx_damage <= 0) return Optional.empty();
			int new_damage = dmg.getDamage() + amount.get(target);
			if (new_damage >= mx_damage) {
				item.setAmount(0);
				Player player = BukkitAdapter.adapt(target.asPlayer());
				switch (this.slot) {
					case HAND -> player.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND);
					case OFF_HAND -> player.playEffect(EntityEffect.BREAK_EQUIPMENT_OFF_HAND);
					case HEAD -> player.playEffect(EntityEffect.BREAK_EQUIPMENT_HELMET);
					case CHEST -> player.playEffect(EntityEffect.BREAK_EQUIPMENT_CHESTPLATE);
					case LEGS -> player.playEffect(EntityEffect.BREAK_EQUIPMENT_LEGGINGS);
					case BODY -> player.playEffect(EntityEffect.BREAK_EQUIPMENT_BODY);
					default -> player.playEffect(EntityEffect.BREAK_EQUIPMENT_MAIN_HAND);
				}
				return Optional.empty();
			}
			
			dmg.setDamage(new_damage);
			item.setItemMeta(meta);
			
			return Optional.of(item);
		}
		
		return Optional.empty();
	}
}