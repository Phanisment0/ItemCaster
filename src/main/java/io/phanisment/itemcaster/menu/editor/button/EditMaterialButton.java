package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.List;

public class EditMaterialButton extends ItemEditorButton {
	public EditMaterialButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.GRASS_BLOCK)
		.name("<color:#32ed73>Set Material")
		.lore(context -> List.of("", "<color:#e5ed4e>Current:</color> <white>" + String.valueOf(context.getItem().getMaterial())))
		.click((context, player) -> {
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the item type (Material) you'd like to use.", "Type cancel to abort editing.");
			player.closeInventory();
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				if (!context.getItem().setMaterial(input.toUpperCase())) {
					CommandHelper.sendError((CommandSender)player, "Failed to set material: invalid material specified.");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> context.openMenu(player));
		}).build();
	}
}