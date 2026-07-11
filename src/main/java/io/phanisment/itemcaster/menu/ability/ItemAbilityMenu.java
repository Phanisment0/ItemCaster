package io.phanisment.itemcaster.menu.ability;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.menu.ability.helper.ActivatorListMenuContext;
import io.phanisment.itemcaster.menu.ability.helper.SkillListMenuContext;
import io.phanisment.itemcaster.util.CasterColorCode;

public class ItemAbilityMenu extends CasterMenu<ItemAbilityMenuContext> {
	public ItemAbilityMenu() {
		super("menus/item-ability.yml");
	}

	@Override
	public void open(Player player, ItemAbilityMenuContext context) {
		this.open(player, context, "Edit: " + context.item().getId(), MenuManager.ITEM_ABILITY_BUTTONS.buttons());
	}

	@Override
	public EditableMenuBuilder<ItemAbilityMenuContext> build(EditableMenuBuilder<ItemAbilityMenuContext> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(i -> i.getBuilder().click((ctx, p) -> ctx.openPrevious(p)));
		builder.getIcon("GIVE_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> p.getInventory().addItem(ctx.item().getItemStack())));
		builder.getIcon("ITEM_BUTTON").ifPresent(i -> i.getBuilder().itemStack(ctx -> ctx.item().getItemStack()).hideFlags(false));
		builder.getIcon("ABILITY_BUTTON").ifPresent(i -> i.getBuilder().itemStack(ctx -> new ItemStack(Material.ARMOR_STAND)).name(ctx -> CasterColorCode.BLUE + ctx.attribute().skill() + "<white>: " + ctx.attribute().activator()).lore(List.of(
			"",
			CasterColorCode.ORANGE + "✎ <gray>L - Change the Skill",
			CasterColorCode.ORANGE + "✎ <gray>R - Change the Activator",
			CasterColorCode.RED + "✉ <gray>S+L - List of Skills",
			CasterColorCode.RED + "✉ <gray>S+R - List of Activators"
		)).click(this::editSkill).rightClick(this::editActivator).shiftClick(this::skillMenu).shiftRightClick(this::activatorMenu));
		return builder;
	}

	private void editSkill(ItemAbilityMenuContext ctx, Player p) {
		p.closeInventory();
		ChatPrompt.listen(p, i -> {
			if (i.equalsIgnoreCase("cancel")) return Response.ACCEPTED;
			ctx.attribute().skill(i);
			ctx.item().setAbility(ctx.index(), ctx.attribute());
			return Response.ACCEPTED;
		}).thenAcceptSync(in -> ctx.open(p));
	}

	private void editActivator(ItemAbilityMenuContext ctx, Player p) {
		p.closeInventory();
		ChatPrompt.listen(p, i -> {
			if (i.equalsIgnoreCase("cancel")) return Response.ACCEPTED;
			ctx.attribute().skill(i);
			ctx.item().setAbility(ctx.index(), ctx.attribute());
			return Response.ACCEPTED;
		}).thenAcceptSync(in -> ctx.open(p));
	}

	private void skillMenu(ItemAbilityMenuContext ctx, Player p) {
		MenuManager.SKILL_LIST_MENU.open(p, new SkillListMenuContext(ctx.item(), ctx, ctx.attribute(), ctx.index()));
	}

	private void activatorMenu(ItemAbilityMenuContext ctx, Player p) {
		MenuManager.ACTIVATOR_LIST_MENU.open(p, new ActivatorListMenuContext(ctx.item(), ctx, ctx.attribute(), ctx.index()));
	}
}