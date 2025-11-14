package io.phanisment.itemcaster.menu;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;
import fr.mrmicky.fastinv.PaginatedFastInv;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.AbilitiesMenu;

public class MenuEditor extends PaginatedFastInv {
  private static final InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');
  private final CasterItem item;

  public MenuEditor(CasterItem item) {
    super(54, "Item Editor");  
    this.item = item;
    addItem(new ItemBuilder(Material.BLAZE_POWDER).name("Edit Ability").build(), e -> {
      new AbilitiesMenu(this.item).open((Player)e.getWhoClicked());
    });
    scheme.apply(this);
   }
}