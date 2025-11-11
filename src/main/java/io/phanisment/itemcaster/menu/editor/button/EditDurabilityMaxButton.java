package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.api.skills.placeholders.PlaceholderInt;
import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import io.phanisment.itemcaster.item.CasterItem;

import java.util.List;

public class EditDurabilityMaxButton extends ItemEditorButton {
	public EditDurabilityMaxButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public boolean isApplicable(MythicItem mythicItem) {
		return !mythicItem.isUnbreakable() && mythicItem.getMaterial().getMaxDurability() > 0;
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.ANVIL)
		.name("<color:#32ed73>Set Maximum Durability")
		.lore(context -> List.of(
			"",
			"<color:#e5ed4e>Current:</color> <white>" + context.getItem().getMaxDurability(),
			"",
			"<gray>Click to edit",
			"<gray>Shift-Click to unset"
		)).click((context, player) -> {
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the maximum durability you'd like to use.", "(Can be a placeholder that evaluates to an integer)", "Type cancel to abort editing.");
			player.closeInventory();
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				MythicItem item = context.getItem();
				MythicConfig config = item.getConfig();
				item.setMaxDurability(PlaceholderInt.of(input));
				String key = config.determineWhichKeyToUse("Options.MaxDurability", "MaxDurability", "CustomDurability.Durability");
				config.setSave(key, input);
				item.buildItemCache();
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> context.openMenu(player));
		}).shiftClick((context, player) -> {
			MythicItem item = context.getItem();
			MythicConfig config = item.getConfig();
			item.setMaxDurability(PlaceholderInt.of("0"));
			String key = config.determineWhichKeyToUse("Options.MaxDurability", "MaxDurability", "CustomDurability.Durability");
			config.unsetSave(key);
			item.buildItemCache();
		}).build();
	}
}