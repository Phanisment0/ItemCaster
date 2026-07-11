package io.phanisment.itemcaster.menu.ability.helper;

import static io.phanisment.itemcaster.ItemCaster.core;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.menu.MenuData;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt;
import io.lumine.mythic.bukkit.utils.prompts.chat.ChatPrompt.Response;
import io.phanisment.itemcaster.menu.CasterMenu;

public class SkillListMenu extends CasterMenu<SkillListMenuContext> {
	public SkillListMenu() {
		super("menus/skill-list.yml");
	}

	@Override
	public void open(Player player, SkillListMenuContext ctx) {
		List<SkillIcon> list = new ArrayList<>();
		for (String name : core().getSkillManager().getSkillNames()) list.add(new SkillIcon(name));
		if (ctx.filter() != null) list.removeIf(icon -> !icon.display.toLowerCase().contains(ctx.filter().toLowerCase()));
		this.open(player, ctx, list);
	}

	@Override
	public EditableMenuBuilder<SkillListMenuContext> build(EditableMenuBuilder<SkillListMenuContext> builder) {
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

	public static record SkillIcon(String display) implements MenuData<SkillListMenuContext> {
		@Override
		public Icon<SkillListMenuContext> getIcon() {
			return IconBuilder.<SkillListMenuContext>create().material(Material.GRAY_DYE).name("<white>" + display).click((ctx, p) -> {
				ctx.attribute().skill(display);
				ctx.item().setAbility(ctx.index(), ctx.attribute());
				ctx.openPrevious(p);
			}).build();
		}
	}
}
