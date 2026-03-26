package io.phanisment.itemcaster.menu.editor.ability;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import fr.mrmicky.fastinv.PaginatedFastInv;
import fr.mrmicky.fastinv.InventoryScheme;
import fr.mrmicky.fastinv.ItemBuilder;

import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu.AbilityMenuContext;
import io.phanisment.itemcaster.parser.VariableParser;
import io.phanisment.itemcaster.parser.VariableParser.VariableData;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;

import java.util.Map;

public class VariablesMenu extends PaginatedFastInv {
	private InventoryScheme scheme = new InventoryScheme()
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.mask("111111111")
		.bindPagination('1');

	public VariablesMenu(CasterItem item, AbilityMenuContext ctx) {
		super(54, "List of Variable Ability slot: " + ctx.index()); 
		
		previousPageItem(52, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Previous")).build());
		nextPageItem(53, new ItemBuilder(Material.ARROW).name(Legacy.serializer("<white>Next")).build());

		Map<String, Object> variables = ctx.data().variables;
		
		setItem(45, new ItemBuilder(Material.BARRIER).name(Legacy.serializer("<white>Back")).build(), e -> {
			Player player = (Player) e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			new AbilityMenu(item, ctx).open(player);
		});
		
		setItem(48, new ItemBuilder(Material.ENDER_CHEST).name(Legacy.serializer("<white>Add Variable")).build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			player.closeInventory();
			CasterLogger.send(player, VariableParser.CREATE_GUIDE);
			ChatPrompt.listen(player, i -> {
				if (i.equalsIgnoreCase("cancel")) {
					CasterLogger.send(player, "<green>Cancelled!");
					return ChatPrompt.Response.ACCEPTED;
				}
				VariableData var_data = VariableParser.parseKey(i);
				variables.put(var_data.key(), var_data.value());
				ctx.data().variables = variables;
				item.setAbility(ctx.index(), ctx.data());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> new VariablesMenu(item, ctx).open(player));
		});
		
		setItem(49, item.getItemStack(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			player.getInventory().addItem(item.getItemStack(1));
		});
		
		for (Map.Entry<String, Object> entry : variables.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			addContent(new ItemBuilder(Material.CHEST).name("§f" + key + ": §e" + value).lore("§8(" + VariableParser.getType(value) + ")").build(), e -> {
				Player player = (Player)e.getWhoClicked();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
				if (e.getClick().equals(ClickType.RIGHT)) {
					variables.remove(key);
					ctx.data().variables = variables;
					item.setAbility(ctx.index(), ctx.data());
					new VariablesMenu(item, ctx).open(player);
					return;
				} else if (e.getClick().equals(ClickType.LEFT)) {
					player.closeInventory();
					CasterLogger.send(player, VariableParser.EDIT_GUIDE);
					ChatPrompt.listen(player, i -> {
						if (i.equalsIgnoreCase("cancel")) {
							CasterLogger.send(player, "<green>Cancelled!");
							return ChatPrompt.Response.ACCEPTED;
						}
						Object new_value = VariableParser.parseValue(i);
						variables.put(key, new_value);
						ctx.data().variables = variables;
						item.setAbility(ctx.index(), ctx.data());
						return ChatPrompt.Response.ACCEPTED;
					}).thenAcceptSync(in -> new VariablesMenu(item, ctx).open(player));
				}
			});
		}
		scheme.apply(this);
	}
}