package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.skills.placeholders.PlaceholderString;
import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.commands.items.edit.ItemEditCommand;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.ArrayList;
import java.util.List;

public class EditLoreButton extends ItemEditorButton {
	public EditLoreButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.WRITABLE_BOOK)
		.name("<color:#32ed73>Edit Lore")
		.lore(context -> {
			List<String> lore = List.of("", "<color:#e5ed4e>Current:</color>", "");
			lore.addAll(context.getItem().getLore());
			lore.addAll(List.of(
				"",
				"<gray>Left-click add a line", 
				"<gray>Right-click to edit lines"
			));
			return lore;
		}).click((context, player) -> {
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the Lore line you'd like to add.", "Type cancel to abort editing.");
			player.closeInventory();
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				MythicItem item = context.getItem();
				List<PlaceholderString> lore = item.getLoreRaw();
				lore.add(PlaceholderString.of(input));
				item.setLore(lore);
				List<String> saveList = new ArrayList<>();
				for (PlaceholderString pl : lore) {
					saveList.add(pl.toString());
				}
				item.getConfig().setSave("Lore", saveList);
				item.buildItemCache();
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> context.openMenu((Player)player));
		}).rightClick((context, player) -> {
			ItemEditCommand.lastEdited.put(player.getUniqueId(), (ItemEditorMenuContext)context);
			player.performCommand("mm i edit lore " + context.getItem().getInternalName());
			player.closeInventory();
		}).build();
	}
}