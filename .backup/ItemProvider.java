package io.phanisment.itemcaster.item;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.bukkit.BukkitAdapter;
import io.lumine.mythic.bukkit.MythicBukkit;
import io.lumine.mythic.core.items.MythicItem;
import io.phanisment.itemcaster.item.external.IExternalItem;
import io.phanisment.itemcaster.registry.ExternalItemRegistry;
import io.phanisment.itemcaster.util.Identifier;

public class ItemProvider {
	private static final String MYTHICMOBS = "mythicmobs";
	private Identifier id;
	private MythicItem mi;

	public ItemProvider(Identifier id) {
		this(id, null);
	}

	public ItemProvider(Identifier id, MythicItem mi) {
		this.id = id;
		this.mi = mi;
	}

	public ItemStack get(boolean include_mythicmobs) {
		if (include_mythicmobs && id.namespace().equals(MYTHICMOBS)) {
			Optional<MythicItem> mi = MythicBukkit.inst().getItemManager().getItem(id.path());
			if (mi.isPresent()) return BukkitAdapter.adapt(mi.get().generateItemStack(1));
		} else {
			IExternalItem ei = ExternalItemRegistry.getRegistered(id.path());
			if (ei != null) return ei.resolve(id, mi);
		}

		try {
			return new ItemStack(Material.valueOf(id.path()));
		} catch(IllegalArgumentException e) {
			return null;
		}
	}
}
