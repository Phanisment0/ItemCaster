package io.phanisment.itemcaster.menu.editor.button;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.commands.CommandHelper;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.serialize.Chroma;
import io.lumine.mythic.core.items.MythicItem;
import io.lumine.mythic.core.menus.items.ItemEditorButton;
import io.lumine.mythic.core.menus.items.ItemEditorButtons;
import io.lumine.mythic.core.menus.items.ItemEditorMenuContext;

import io.phanisment.itemcaster.ItemCaster;

import java.util.List;

public class EditColorButton extends ItemEditorButton {
	private static final List<Material> colorable_items = List.of(
		Material.LEATHER_HORSE_ARMOR,
		Material.LEATHER_HELMET,
		Material.LEATHER_CHESTPLATE,
		Material.LEATHER_LEGGINGS,
		Material.LEATHER_BOOTS,
		Material.POTION,
		Material.LINGERING_POTION,
		Material.SPLASH_POTION,
		Material.SHIELD
	);
	
	public EditColorButton(ItemEditorButtons parent, int sort) {
		super(parent, sort);
	}
	
	@Override
	public boolean isApplicable(MythicItem mythicItem) {
		return colorable_items.contains(mythicItem.getMaterial());
	}
	
	@Override
	public Icon<ItemEditorMenuContext> getIcon() {
		return IconBuilder.<ItemEditorMenuContext>create()
		.material(Material.RED_DYE)
		.name("<color:#32ed73>Set Color")
		.lore(context -> {
			String hex = context.getItem().getColor().get().toHexString();
			return List.of(
				"",
				"<color:#e5ed4e>Current:</color> <color:" + hex + ">█ " + hex,
				"",
				"<gray>Shift-Click to unset",
				"<gray>Left-Click to open Color Picker", 
				"<gray>Right-Click to supply Placeholder"
			);
		}).click((context, player) -> {
			MythicItem item = context.getItem();
			Chroma existing_color = item.getColor() == null ? null : item.getColor().get();
			ItemCaster.core().getMenuManager().getColorPickerMenu().openMenu(player, existing_color).thenAcceptSync(color -> {
				item.getConfig().setSave("Options.Color", color.serializeShortForm());
				item.loadItem();
				item.buildItemCache();
				context.openMenu(player);
			});
		}).shiftClick((context, player) -> {
			MythicItem item = context.getItem();
			item.getConfig().unsetSave("Options.Color");
			item.loadItem();
			item.buildItemCache();
		}).rightClick((context, player) -> {
			CommandHelper.sendEditorMessage((CommandSender)player, "Type in the Color you'd like to use.", "Type cancel to abort editing.");
			player.closeInventory();
			ChatPrompt.listen(player, input -> {
				if (input.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				try {
					var color = Chroma.of(input);
					if (color == null) {
						CommandHelper.sendError((CommandSender)player, "Invalid color format supplied");
						return ChatPrompt.Response.TRY_AGAIN;
					}
					MythicItem item = context.getItem();
					item.getConfig().setSave("Options.Color", color.serializeShortForm());
					item.loadItem();
					item.buildItemCache();
				} catch (Throwable ex) {
					CommandHelper.sendError((CommandSender)player, "Invalid color format supplied");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> context.openMenu(player));
		}).build();
	}
}