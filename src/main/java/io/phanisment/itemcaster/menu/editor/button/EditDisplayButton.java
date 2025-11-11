package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.List;

public class EditDisplayButton extends ItemEditorButton {
	public EditDisplayButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.DARK_OAK_SIGN)
		.name("<color:#32ed73>Set Display Name")
		.lore(context -> List.of(
			"",
			"<color:#e5ed4e>Current:</color> <white>" + context.getItem().getDisplayName(),
			"",
			"<gray>Click to edit",
			"<gray>Shift-Click to unset"
		)).click((context, player) -> {
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the Display Name you'd like to use.", "Type cancel to abort editing.");
			player.closeInventory();
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				MythicItem item = context.getItem();
				item.setDisplayName(input);
				item.getConfig().setSave("Display", input);
				item.buildItemCache();
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> context.openMenu(player));
		}).shiftClick((context, player) -> {
			MythicItem item = context.getItem();
			item.setDisplayName(null);
			item.getConfig().unsetSave("Display");
			item.buildItemCache();
		}).build();
	}
}

