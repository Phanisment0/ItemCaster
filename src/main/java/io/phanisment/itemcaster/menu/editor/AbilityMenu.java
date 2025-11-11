package io.phanisment.itemcaster.menu.editor;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.command.CommandSender;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;

import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.commands.CommandHelper;

import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.AbilitiesMenu.AbilitiesMenuContext;

public class AbilityMenu extends FastInv {
	public AbilityMenu(CasterItem item, AbilitiesMenuContext ctx) {
		super(54, "Item Ability slot: " + ctx.index());
		
		setItem(45, new ItemBuilder(Material.STRUCTURE_VOID)
		.name("§fBack")
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			new AbilitiesMenu(item).open(player);
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
		});
		
		setItem(49, item.getItemStack(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.performCommand("mythicmobs i get " + item.getName());
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
		});
		
		// Skill
		setItem(0, new ItemBuilder(Material.BOOK)
		.name("§fSet Skill: §e" + ctx.data().getSkill())
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			player.closeInventory();
			ChatPrompt.listen(player, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				ctx.data().setSkill(i);
				item.setAbility(ctx.index(), ctx.data());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
		});
		
		// Activator
		setItem(1, new ItemBuilder(Material.BEACON)
		.name("§fSet Activator: §e" + ctx.data().getActivator())
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			player.closeInventory();
			ChatPrompt.listen(player, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				ctx.data().setActivator(i);
				item.setAbility(ctx.index(), ctx.data());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
		});
		
		// Power
		setItem(2, new ItemBuilder(Material.IRON_SWORD)
		.name("§fSet Power: §e" + ctx.data().getPower())
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			player.closeInventory();
			ChatPrompt.listen(player, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				try {
					ctx.data().setPower(Float.parseFloat(i));
				} catch (NumberFormatException err) {
					CommandHelper.sendError((CommandSender)player, "The value must number, try again.");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				item.setAbility(ctx.index(), ctx.data());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
		});
		
		// Cooldown
		setItem(3, new ItemBuilder(Material.IRON_SWORD)
		.name("§fSet Cooldown: §e" + ctx.data().getCooldown())
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			player.closeInventory();
			ChatPrompt.listen(player, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				try {
					ctx.data().setCooldown(Double.parseDouble(i));
				} catch (NumberFormatException err) {
					CommandHelper.sendError((CommandSender)player, "The value must number, try again.");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				item.setAbility(ctx.index(), ctx.data());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
		});
		
		// Interval
		setItem(4, new ItemBuilder(Material.REPEATER)
		.name("§fSet Interval: §e" + ctx.data().getInterval())
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			player.closeInventory();
			ChatPrompt.listen(player, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				try {
					ctx.data().setInterval(Integer.parseInt(i));
				} catch (NumberFormatException err) {
					CommandHelper.sendError((CommandSender)player, "The value must number, try again.");
					return ChatPrompt.Response.TRY_AGAIN;
				}
				item.setAbility(ctx.index(), ctx.data());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
		});
		
		// Sneaking
		setItem(5, new ItemBuilder(Material.DIAMOND_BOOTS)
		.name("§fIs Sneaking: §e" + ctx.data().getSneaking())
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			ctx.data().setSneaking(!ctx.data().getSneaking());
			item.setAbility(ctx.index(), ctx.data());
			new AbilityMenu(item, ctx).open(player);
		});
		
		// Signal
		setItem(6, new ItemBuilder(Material.REDSTONE_TORCH)
		.name("§fSet Signal: §e" + ctx.data().getSignal())
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			player.closeInventory();
			ChatPrompt.listen(player, i -> {
				if (i.equalsIgnoreCase("cancel")) return ChatPrompt.Response.ACCEPTED;
				ctx.data().setSignal(i);
				item.setAbility(ctx.index(), ctx.data());
				return ChatPrompt.Response.ACCEPTED;
			}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
		});
		
		// Variable
		setItem(7, new ItemBuilder(Material.CHEST)
		.name("§fView Variables")
		.build(), e -> {
			Player player = (Player)e.getWhoClicked();
			player.playSound(player.getLocation(), "entity.experience_orb.pickup", 1.0f, 1.0f);
			new VariablesMenu(item, ctx).open(player);
		});
	}
}