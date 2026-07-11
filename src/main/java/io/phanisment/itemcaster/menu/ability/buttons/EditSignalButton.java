package io.phanisment.itemcaster.menu.ability.buttons;

import java.util.List;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.phanisment.itemcaster.menu.ability.ItemAbilityMenuContext;
import io.phanisment.itemcaster.skill.SkillAttribute.AttributeKeys;
import io.phanisment.itemcaster.util.CasterColorCode;

public class EditSignalButton extends ItemAbilityButton {

	@Override
	public Icon<ItemAbilityMenuContext> getIcon() {
		return IconBuilder.<ItemAbilityMenuContext>create().material(Material.REDSTONE_TORCH).name("Set Signal").lore(ctx -> List.of(
			"<gray>Current: <white>" + ctx.attribute().get(AttributeKeys.SIGNAL, String.class, "null"),
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Change Signal",
			CasterColorCode.MAGENTA + "☈ <gray>R - Unset"
		)).click((ctx, p) -> {
			playMenuClick(p);
			p.closeInventory();
			ChatPrompt.listen(p, i -> {
				ctx.attribute().set(AttributeKeys.SIGNAL, String.class, i);
				ctx.item().setAbility(ctx.index(), ctx.attribute());
				return Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			ctx.attribute().unset(AttributeKeys.SIGNAL);
			ctx.item().setAbility(ctx.index(), ctx.attribute());
			ctx.open(p);
		}).build();
	}
}
