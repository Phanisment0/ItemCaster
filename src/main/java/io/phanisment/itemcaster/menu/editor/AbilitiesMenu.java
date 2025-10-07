package io.phanisment.itemcaster.menu.editor;

import fr.mrmicky.fastinv.PaginatedFastInv;
import io.phanisment.itemcaster.item.CasterItem;
import fr.mrmicky.fastinv.ItemBuilder;

import java.util.Map;
import java.util.List;

import org.bukkit.Material;

import fr.mrmicky.fastinv.InventoryScheme;

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
		nextPageItem(54, new ItemBuilder(Material.ARROW).name("Next Page").build());
		previousPageItem(53, new ItemBuilder(Material.ARROW).name("Previous Page").build());
		List<Map<String, Object>> abilities = item.getAbilities();
		int index_ability = 0;
		for (Map<String, Object> ability : abilities) {
			index_ability++;
			addContent(new ItemBuilder(Material.GRAY_DYE).name("Ability Slot: " + index_ability).build(), e -> {

			});
		}
		scheme.apply(this);
	}
}