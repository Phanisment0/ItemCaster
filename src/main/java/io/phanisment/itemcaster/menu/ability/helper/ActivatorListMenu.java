package io.phanisment.itemcaster.menu.ability.helper;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.menu.MenuData;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;

public class ActivatorListMenu extends CasterMenu<ActivatorListMenuContext> {

	public ActivatorListMenu() {
		super("menus/activator-list.yml");
	}

	@Override
	public void open(Player player, ActivatorListMenuContext ctx) {
		List<ActivatorIcon> list = Lists.newArrayList(MenuManager.LIST_ACTIVATOR);
		if (ctx.filter() != null) list.removeIf(icon -> !icon.display.toLowerCase().contains(ctx.filter().toLowerCase()));
		this.open(player, ctx, list);
	}

	@Override
	public EditableMenuBuilder<ActivatorListMenuContext> build(EditableMenuBuilder<ActivatorListMenuContext> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(i -> i.getBuilder().click((ctx, p) -> ctx.openPrevious(p)));
		builder.getIcon("FILTER_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> {
			playMenuClick(p);
			p.closeInventory();
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return Response.ACCEPTED;
				ctx.filter(i);
				return Response.ACCEPTED;
			}).thenAcceptSync(in -> ctx.open(p));
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
			ctx.filter(null);
			ctx.open(p);
		}));
		return builder;
	}

	public static record ActivatorIcon(String display) implements MenuData<ActivatorListMenuContext> {
		@Override
		public Icon<ActivatorListMenuContext> getIcon() {
			return IconBuilder.<ActivatorListMenuContext>create().material(Material.GRAY_DYE).name("<white>" + display).click((ctx, p) -> {
				ctx.attribute().activator(display);
				ctx.item().setAbility(ctx.index(), ctx.attribute());
				ctx.openPrevious(p);
			}).build();
		}
	}
}
