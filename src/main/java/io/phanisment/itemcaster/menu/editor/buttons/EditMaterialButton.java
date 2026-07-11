package io.phanisment.itemcaster.menu.editor.buttons;

import java.util.List;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.editor.ItemEditorMenuContext;
import io.phanisment.itemcaster.menu.editor.helper.MaterialListMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

public class EditMaterialButton extends ItemEditorButton {

	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create().material(Material.GRASS_BLOCK).name("<white>Edit Material").lore(ctx -> List.of(
			"<gray>Current: <white>" + ctx.item().getItem().getMaterial(),
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Change Material",
			CasterColorCode.MAGENTA + "☈ <gray>R - Unset",
			CasterColorCode.RED + "✉ <gray>S+L - Open Menu"
		)).click((ctx, p) -> {
			playMenuClick(p);
			if (ctx.item().hasModelData()) {
				CasterLogger.send(p, CasterColorCode.RED + "You can't edit this, reset IemModel if you want edit this");
				return;
			}

			p.closeInventory();
			CasterLogger.send(p, CasterColorCode.YELLOW + "Enter the new material id for the item (e.g. DIAMOND_SWORD). Type 'cancel' to cancel.");
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) {
					CasterLogger.send(p, "<green>Cancelled!");
					return ChatPrompt.Response.ACCEPTED;
				}
				if (!ctx.item().getItem().setMaterial(i.toUpperCase())) {
					CasterLogger.send(p, "Unkown material id: " + i.toUpperCase() + ", try again");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
			ctx.item().getItem().setMaterial("STONE");
			ctx.open(p);
		}).shiftClick((ctx, p) -> {
			MenuManager.MATERIAL_LIST_MENU.open(p, new MaterialListMenuContext(ctx.item(), ctx));
		}).build();
	}
}