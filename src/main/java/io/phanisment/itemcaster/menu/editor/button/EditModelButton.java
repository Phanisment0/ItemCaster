package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.numbers.Numbers;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.List;
import java.util.Optional;

public class EditModelButton extends ItemEditorButton {
	public EditModelButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.PAINTING)
		.name("<color:#32ed73>Set Model (CustomModelData)")
		.lore(context -> List.of("", "<color:#e5ed4e>Current:</color> <white>" + context.getItem().getCustomModelData()))
		.click((context, player) -> {
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the CustomModelData you'd like to use.", "Type cancel to abort editing.");
			player.closeInventory();
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				Optional<Integer> maybeInt = Numbers.parseIntegerOpt(input);
				if (maybeInt.isEmpty()) {
					CommandHelper.sendError((CommandSender)player, "Failed to set CustomModelData: invalid value specified.");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				MythicItem item = context.getItem();
				item.setCustomModelData(maybeInt.get());
				item.getConfig().setSave("Model", maybeInt.get());
				item.buildItemCache();
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> context.openMenu(player));
		}).build();
	}
}

