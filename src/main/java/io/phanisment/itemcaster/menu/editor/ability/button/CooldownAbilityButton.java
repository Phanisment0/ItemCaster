package io.phanisment.itemcaster.menu.editor.ability.button;

import java.util.Optional;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

import fr.mrmicky.fastinv.ItemBuilder;

import io.lumine.mythic.api.skills.Skill;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;

import io.phanisment.itemcaster.ItemCaster;
import io.phanisment.itemcaster.item.CasterItem;
import io.phanisment.itemcaster.menu.editor.ability.AbilitiesMenu.AbilityMenuContext;
import io.phanisment.itemcaster.menu.editor.ability.AbilityMenu;
import io.phanisment.itemcaster.util.CasterLogger;
import io.phanisment.itemcaster.util.Legacy;
import io.phanisment.itemcaster.util.MythicMobsUtil;

public class CooldownAbilityButton implements IAbilityButton {
	private static String COOLDOWN = "Cooldown";

	@Override
	public ItemBuilder icon(CasterItem item, AbilityMenuContext ctx) {
		return new ItemBuilder(Material.CLOCK)
		.name(Legacy.serializer("<white>Cooldown"))
		.lore(Legacy.serializer(
			"<dark_gray>Current: <white>" + ctx.data().cooldown,
			"",
			"<gray>Left - Click to edit",
			"<gray>Right - Click to remove"
		));
	}
	
	@Override
	public void left(InventoryClickEvent e, CasterItem item, AbilityMenuContext ctx) {
		Player player = (Player)e.getWhoClicked();
		CasterLogger.send(player, "<yellow>Enter the new cooldown for the ability. Type 'cancel' to cancel.");
		player.closeInventory();
		ChatPrompt.listen(player, i -> {
			if (i.equalsIgnoreCase("cancel")) {
				CasterLogger.send(player, "<green>Cancelled!");
				return ChatPrompt.Response.ACCEPTED;
			}
			try {
				ctx.data().cooldown = Double.parseDouble(i);
			} catch (NumberFormatException err) {
				CasterLogger.send(player, "The value must number, try again.");
				return ChatPrompt.Response.TRY_AGAIN;
			}
			item.setAbility(ctx.index(), ctx.data());
			save(ctx);
			
			return ChatPrompt.Response.ACCEPTED;
		}).thenAcceptSync(in -> new AbilityMenu(item, ctx).open(player));
	}
	
	@Override
	public void right(InventoryClickEvent e, CasterItem item, AbilityMenuContext ctx) {
		ctx.data().cooldown = null;
		item.setAbility(ctx.index(), ctx.data());
		new AbilityMenu(item, ctx).open((Player)e.getWhoClicked());
	}

	private void save(AbilityMenuContext ctx) {
		if (!ItemCaster.config().auto_set_skill_cooldown) return;
		
		Optional<Skill> mybe_skill = MythicMobsUtil.toSkill(ctx.data().skill);
		if (!mybe_skill.isPresent()) return;
		
		Skill skill = mybe_skill.get();
		skill.getConfig().setSave(COOLDOWN, 0);
		ItemCaster.core().getSkillManager().loadSkills();
	}
}