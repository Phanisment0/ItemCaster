package io.phanisment.itemcaster.menu;

import org.bukkit.entity.Player;

import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.PaginatedFastInv;
import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.core.items.MythicItem;
import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.item.CasterItem;

public class MenuBrowser extends PaginatedFastInv {
  private static final InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');
	
    public MenuBrowser() {
      super(54, "Item Browser");
      
      for (MythicItem item : ItemCaster.core().getItemManager().getItems()) {
        addItem(BukkitAdapter.adapt(item.generateItemStack(1)), e -> {
            CasterItem ci = CasterItem.getCasterItem(item).get();
            new MenuEditor(ci).open((Player)e.getWhoClicked());
        });
        scheme.apply(this);
      }
    }
}
