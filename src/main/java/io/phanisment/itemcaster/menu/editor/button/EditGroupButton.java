package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.api.config.MythicConfig;
import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import java.util.List;

public class EditGroupButton extends ItemEditorButton {
	public EditGroupButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}

	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.CHEST)
		.name("<color:#32ed73>Set Group/ItemType")
		.lore(context -> List.of(
			"",
			"<color:#e5ed4e>Current:</color> <white>" + context.getItem().getGroup(),
			"",
			"<gray>Click to edit",
			"<gray>Shift-Click to unset"
		)).click((context, player) -> {
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the Group you'd like to assign.", "Type cancel to abort editing.");
			player.closeInventory();
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				MythicItem item = context.getItem();
				MythicConfig config = item.getConfig();
				item.setGroup(input);
				String key = config.determineWhichKeyToUse("Group", "ItemType", "Options.Group", "Options.ItemType");
				config.setSave(key, input);
				item.buildItemCache();
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> context.openMenu(player));
		}).shiftClick((context, player) -> {
			MythicItem item = context.getItem();
			MythicConfig config = item.getConfig();
			item.setGroup("OTHER");
			String key = config.determineWhichKeyToUse("Group", "ItemType", "Options.Group", "Options.ItemType");
			config.unsetSave(key);
			item.buildItemCache();
		}).build();
	}
}

