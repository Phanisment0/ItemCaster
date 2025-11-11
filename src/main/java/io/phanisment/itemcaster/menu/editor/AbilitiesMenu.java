package io.phanisment.itemcaster.menu.editor;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.mrmicky.fastinv.PaginatedFastInv;
import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;

import io.phanisment.itemcaster.util.Legacy;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.skill.SkillAttribute;

import java.util.Map;
import java.util.List;
import java.util.ArrayList;

public class AbilitiesMenu extends PaginatedFastInv {
	private InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');
	
	public AbilitiesMenu(CasterItem item) {
		super(54, "Item Abilities");
		
		setItem(45, new ItemBuilder(Material.STRUCTURE_VOID)
		.name("§fBack")
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.performCommand("mythicmobs i edit " + item.getName());
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
		});
		
		setItem(48, new ItemBuilder(Material.ENDER_CHEST)
		.name("§aAdd Ability")
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			item.addAbility(new SkillAttribute());
			new AbilitiesMenu(item).open(player);
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
		});
		
		setItem(49, item.getItemStack(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.performCommand("mythicmobs i get " + item.getName());
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
		});
		
		nextPageItem(53, new ItemBuilder(Material.ARROW).name("§fNext Page").build());
		previousPageItem(52, new ItemBuilder(Material.ARROW).name("§fPrevious Page").build());
		List<Map<String, Object>> abilities = item.getAbilities();
		
		for (int i = 0; i < abilities.size(); i++) {
			final int index = i;
			Map<String, Object> ability = abilities.get(i);
			var data = SkillAttribute.fromMap(ability);
			var context = new AbilitiesMenuContext(i, data);
			addContent(new ItemBuilder(Material.BLAZE_POWDER)
			.name("§fAbility Slot: §c" + i)
			.lore(data.toStringList()).build(), e -> {
				Player player = (Player)e.getWhoClicked();
				player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
				if (e.isShiftClick()) {
					item.removeAbility(index);
					new AbilitiesMenu(item).open(player);
					return;
				}
				new AbilityMenu(item, context).open(player);
			});
		}
		scheme.apply(this);
	}
	
	public static record AbilitiesMenuContext(int index, SkillAttribute data) {
	}
}