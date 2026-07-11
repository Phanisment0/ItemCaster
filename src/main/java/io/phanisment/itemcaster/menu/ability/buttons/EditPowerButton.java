package io.phanisment.itemcaster.menu.ability.buttons;

import java.util.List;
import java.util.Optional;

import org.bukkit.Material;

import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.numbers.Numbers;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.phanisment.itemcaster.menu.ability.ItemAbilityMenuContext;
import io.phanisment.itemcaster.skill.SkillAttribute.AttributeKeys;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

public class EditPowerButton extends ItemAbilityButton {

	@Override
	public Icon<ItemAbilityMenuContext> getIcon() {
		return IconBuilder.<ItemAbilityMenuContext>create().material(Material.REDSTONE).name("Set Power").lore(ctx -> List.of(
			"<gray>Current: <white>" + ctx.attribute().get(AttributeKeys.POWER, Float.class, 1f),
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Change Power",
			CasterColorCode.MAGENTA + "☈ <gray>R - Unset"
		)).click((ctx, p) -> {
			p.closeInventory();
			ChatPrompt.listen(p, i -> {
				Optional<Float> value = Numbers.parseFloatOpt(i);
				if (value.isEmpty()) {
					CasterLogger.send(p, "Failed to set Power: invalid value specified.");
					return Response.TRY_AGAIN;
				}
				ctx.attribute().set(AttributeKeys.POWER, Float.class, value.get());
				ctx.item().setAbility(ctx.index(), ctx.attribute());
				return Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			ctx.attribute().unset(AttributeKeys.POWER);
			ctx.item().setAbility(ctx.index(), ctx.attribute());
			ctx.open(p);
		}).build();
	}
}
