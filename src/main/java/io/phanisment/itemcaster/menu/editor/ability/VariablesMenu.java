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

		Map<String, Object> variables = ctx.data().getVariables();
		
		setItem(45, new ItemBuilder(Material.BARRIER).name(Legacy.serializer("<white>Back")).build(), e -> {
			Player player = (Player) e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			new AbilityMenu(item, ctx).open(player);
		});
		
		setItem(48, new ItemBuilder(Material.ENDER_CHEST).name(Legacy.serializer("<white>Add Variable")).build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
			player.closeInventory();
			ChatPrompt.listen(player, i -> {
				String[] parts = i.split("=", 2);
				if (parts.length != 2) return ChatPrompt.Response.TRY_AGAIN;;
				
				String key_var = parts[0].trim();
				Object value_var = formatValue(parts[1].trim());
				
				variables.put(key_var, value_var);
				ctx.data().setVariables(variables);
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
			addContent(new ItemBuilder(Material.CHEST).name("§f" + key + ": §e" + value).lore("§8(" + instanceOf(value) + ")").build(), e -> {
				Player player = (Player)e.getWhoClicked();
				player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BIT, 2f, 2f);
				if (e.getClick().equals(ClickType.RIGHT)) {
					variables.remove(key);
					ctx.data().setVariables(variables);
					item.setAbility(ctx.index(), ctx.data());
					new VariablesMenu(item, ctx).open(player);
					return;
				} else if (e.getClick().equals(ClickType.LEFT)) {
					player.closeInventory();
					ChatPrompt.listen(player, i -> {
						variables.put(key, formatValue(i));
						ctx.data().setVariables(variables);
						item.setAbility(ctx.index(), ctx.data());
						return ChatPrompt.Response.ACCEPTED;
					}).thenAcceptSync(in -> new VariablesMenu(item, ctx).open(player));
				}
			});
		}
		scheme.apply(this);
	}
	
	private Object formatValue(String input) {
		input = input.trim();
		if (input.startsWith("(") && input.contains(")")) {
			return parseTypedValue(input);
		}
		
		if (input.startsWith("\"") && input.endsWith("\"")) {
			return input.substring(1, input.length() - 1);
		}
		
		if (input.equalsIgnoreCase("true")) return true;
		if (input.equalsIgnoreCase("false")) return false;
		
		try {
			return Integer.parseInt(input);
		} catch (NumberFormatException e) {
			
		}
		
		try {
			return Float.parseFloat(input);
		} catch (NumberFormatException e) {
			
		}
		
		return input;
	}
	
	private Object parseTypedValue(String type_value) {
		int end_index = type_value.indexOf(")");
		if (end_index == -1) return type_value;
		
		String type = type_value.substring(1, end_index).trim().toLowerCase();
		String value = type_value.substring(end_index + 1).trim();
		
		switch (type) {
			case "string":
				if (value.startsWith("\"") && value.endsWith("\"")) value = value.substring(1, value.length() - 1);
				return value;
			case "number":
				try {
					if (value.contains(".")) return Float.parseFloat(value);
					return Integer.parseInt(value);
				} catch (NumberFormatException e) {
					return value;
				}
			case "float":
				try {
					return Float.parseFloat(value);
				} catch (NumberFormatException e) {
					return value;
				}
			case "integer":
				try {
					return Integer.parseInt(value);
				} catch (NumberFormatException e) {
					return value;
				}
			case "boolean":
				return Boolean.parseBoolean(value);
			default:
				return value;
		}
	}
	
	private String instanceOf(Object value) {
		if (value instanceof String) return "string";
		if (value instanceof Number) return "number";
		if (value instanceof Boolean) return "boolean";
		return "null";
	}
}