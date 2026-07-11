package io.phanisment.itemcaster.menu.editor.helper;

import org.bukkit.entity.Player;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;

public class MaterialListMenuContext {
	private ItemEditorMenuContext ctx;
	private CasterItem item;
	private String filter = null;

	public MaterialListMenuContext(CasterItem item, ItemEditorMenuContext ctx) {
		this.item = item;
		this.ctx = ctx;
	}

	public void open(Player player) {
		ctx.open(player);
	}

	public void openPrevious(Player player) {
		ctx.openPrevious(player);
	}

	public CasterItem item() {
		return item;
	}

	public ItemEditorMenuContext previousMenu() {
		return this.ctx;
	}

	public void filter(String value) {
		this.filter = value;
	}
	
	public String filter() {
		return filter;
	}
}
