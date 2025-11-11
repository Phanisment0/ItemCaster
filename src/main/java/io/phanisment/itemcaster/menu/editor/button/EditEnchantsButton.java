package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.commands.items.edit.EditEnchantsCommand;
import io.lumine.mythic.bukkit.commands.items.edit.ItemEditCommand;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;
import io.lumine.mythic.core.items.MythicItem;

import java.util.List;
import java.util.Map;

public class EditEnchantsButton extends ItemEditorButton {
	public EditEnchantsButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.ENCHANTING_TABLE)
		.name("<color:#32ed73>Edit Enchantments")
		.lore(context -> {
			MythicItem item = context.getItem();
			List<String> lore = List.of("", "<color:#e5ed4e>Current:</color>");
			for (Map.Entry<Enchantment, PlaceholderInt> entry : item.getEnchantments().entrySet()) {
				lore.add(" <white>" + entry.getKey().getKey().toString() + " " + entry.getValue().toString());
			}
			lore.addAll(List.of(
				"",
				"<gray>Left-click add an enchantment", 
				"<gray>Right-click to edit enchantments"
			));
			return lore;
		}).click((context, player) -> {
			player.closeInventory();
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the Enchantment you'd like to add.", "Format: [enchantment_type] [amount]", "Type cancel to abort editing.");
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				if (!context.getItem().addEnchantment(input)) {
					CommandHelper.sendError((CommandSender)player, "Invalid enchantment supplied");
					CommandHelper.sendEditorMessage((CommandSender)player, "Type in the Enchantment you'd like to add.", "Format: [enchantment_type] [amount]", "Type cancel to abort editing.");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				EditEnchantsCommand.saveEnchants(context.getItem());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> {
				context.getItem().buildItemCache();
				context.openMenu(player);
			});
		}).rightClick((context, player) -> {
			ItemEditCommand.lastEdited.put(player.getUniqueId(), (ItemEditorMenuContext)context);
			player.performCommand("mm i edit enchants " + context.getItem().getInternalName());
			player.closeInventory();
		}).build();
	}
}