package io.phanisment.itemcaster.menu.editor.helper;

import java.util.Comparator;
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

public class MaterialListMenu extends CasterMenu<MaterialListMenuContext> {
	private static final Comparator<MaterialIcon> MATERIAL_COMPARATOR = Comparator.comparing(MaterialIcon::material, String.CASE_INSENSITIVE_ORDER);

	public MaterialListMenu() {
		super("menus/material-list.yml");
	}

	@Override
	public void open(Player player, MaterialListMenuContext ctx) {
		List<MaterialIcon> list = Lists.newArrayList(MenuManager.LIST_MATERIAL);
		if (ctx.filter() != null) list.removeIf(icon -> !icon.material().toLowerCase().contains(ctx.filter().toLowerCase()));
		list.sort(MATERIAL_COMPARATOR);
		this.open(player, ctx, list);
	}

	@Override
	public EditableMenuBuilder<MaterialListMenuContext> build(EditableMenuBuilder<MaterialListMenuContext> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(i -> i.getBuilder().click((ctx, p) -> ctx.open(p)));
		builder.getIcon("FILTER_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> {
			playMenuClick(p);
			p.closeInventory();
			ChatPrompt.listen(p, i -> {
				if (i.equalsIgnoreCase("cancel")) return Response.ACCEPTED;
				ctx.filter(i);
				return Response.ACCEPTED;
			}).thenAcceptSync(in -> this.open(p, ctx));
		}).rightClick((ctx, p) -> {
			playMenuClick(p);
			ctx.filter(null);
			this.open(p, ctx);
		}));
		return builder;
	}

	public static record MaterialIcon(String material) implements MenuData<MaterialListMenuContext> {
		@Override
		public Icon<MaterialListMenuContext> getIcon() {
			return IconBuilder.<MaterialListMenuContext>create().material(Material.valueOf(material)).name("<white>" + material).click((ctx, p) -> {
				ctx.item().getItem().setMaterial(material);
				ctx.open(p);
			}).build();
		}
	}
}
