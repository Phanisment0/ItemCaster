package io.phanisment.itemcaster.menu.editor.ability;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import fr.mrmicky.fastinv.PaginatedFastInv;
import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.MenuEditor;
import io.phanisment.itemcaster.skill.SkillAttribute;
import io.phanisment.itemcaster.util.Legacy;

import java.util.Map;
import java.util.Optional;
import java.util.List;

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
		
		setItem(45, new ItemBuilder(Material.BARRIER).name(Legacy.serializer("<white>Back")).build(), e -> {
			Player player = (Player) e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			new MenuEditor(Optional.of(item)).open(player);
		});
		
		setItem(48, new ItemBuilder(Material.ENDER_CHEST).name(Legacy.serializer("<white>Add Ability")).build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			item.addAbility(new SkillAttribute());
			new AbilitiesMenu(item).open(player);
		});
		
		setItem(49, item.getItemStack(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			player.getInventory().addItem(item.getItemStack(1));
		});
		
		previousPageItem(52, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Previous")).build());
		nextPageItem(53, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Next")).build());
		
		List<Map<String, Object>> abilities = item.getAbilities();
		
		for (int i = 0; i < abilities.size(); i++) {
			final int index = i;
			Map<String, Object> ability = abilities.get(i);
			var data = SkillAttribute.fromMap(ability);
			var context = new AbilityMenuContext(i, data);
			addContent(new ItemBuilder(Material.BLAZE_POWDER).name(Legacy.serializer("<white>Ability Slot: " + index)).build(), e -> {
				Player player = (Player)e.getWhoClicked();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
				if (e.getClick().equals(ClickType.RIGHT)) {
					item.removeAbility(index);
					new AbilitiesMenu(item).open(player);
					return;
				}
				new AbilityMenu(item, context).open(player);
			});
		}
		scheme.apply(this);
	}
	
	public static record AbilityMenuContext(int index, SkillAttribute data) {
	}
}