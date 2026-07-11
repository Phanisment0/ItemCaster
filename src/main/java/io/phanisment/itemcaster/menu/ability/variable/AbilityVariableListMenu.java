package io.phanisment.itemcaster.menu.ability.variable;

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
import io.phanisment.itemcaster.menu.ability.ItemAbilityMenuContext;
import io.phanisment.itemcaster.parser.VariableParser;
import io.phanisment.itemcaster.skill.SkillAttribute.AttributeKeys;
import io.phanisment.itemcaster.util.CasterColorCode;
import io.phanisment.itemcaster.util.CasterLogger;

public class AbilityVariableListMenu extends CasterMenu<ItemAbilityMenuContext> {
	public AbilityVariableListMenu() {
		super("menus/ability-variable-list.yml");
	}

	@Override
	public void open(Player p, ItemAbilityMenuContext ctx) {
		List<VariableIcon> list =  Lists.newArrayList();
		for (var variable : ctx.attribute().variable().entrySet()) list.add(new VariableIcon(variable.getKey(), variable.getValue()));
		this.open(p, ctx, "Edit" + ctx.item().getId(), list);
	}

	@Override
	public EditableMenuBuilder<ItemAbilityMenuContext> build(EditableMenuBuilder<ItemAbilityMenuContext> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(i -> i.getBuilder().click((ctx, p) -> ctx.openPrevious(p)));
		builder.getIcon("GIVE_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> p.getInventory().addItem(ctx.item().getItemStack())));
		builder.getIcon("CREATE_BUTTON").ifPresent(i -> i.getBuilder().click((ctx, p) -> {
			p.closeInventory();
			CasterLogger.send(p, VariableParser.CREATE_GUIDE);
			ChatPrompt.listen(p, v -> {
				try {
					var data = VariableParser.parseKey(v);
					ctx.attribute().variable(data.key(), data.value());
					ctx.item().setAbility(ctx.index(), ctx.attribute());
					return Response.ACCEPTED;
				} catch (Exception e) {
					CasterLogger.send(p, CasterColorCode.RED + "Invalid format");
					return Response.TRY_AGAIN;
				}
			}).thenAcceptSync(in -> MenuManager.ABILITY_VARIABLE_LIST_MENU.open(p, ctx));
		}));
		builder.getIcon("ITEM_BUTTON").ifPresent(i -> i.getBuilder().itemStack(ctx -> ctx.item().getItemStack()).hideFlags(false));
		return builder;
	}

	public static record VariableIcon(String key, Object value) implements MenuData<ItemAbilityMenuContext> {
		@Override
		public Icon<ItemAbilityMenuContext> getIcon() {
			return IconBuilder.<ItemAbilityMenuContext>create().material(Material.CHEST).name(CasterColorCode.MAGENTA + key + "<white>: " + CasterColorCode.LIGHT_BLUE + value.toString()).lore(ctx -> List.of(
				"",
				CasterColorCode.ORANGE + "✎ <gray>L - Edit Value",
				CasterColorCode.MAGENTA + "☈ <gray>R - Delete"
			)).click((ctx, p) -> {
				p.closeInventory();
				CasterLogger.send(p, VariableParser.EDIT_GUIDE);
				ChatPrompt.listen(p, i -> {
					Object value = VariableParser.parseValue(i);
					ctx.attribute().variable(key, value);
					ctx.item().setAbility(ctx.index(), ctx.attribute());
					return Response.ACCEPTED;
				}).thenAcceptSync(in -> MenuManager.ABILITY_VARIABLE_LIST_MENU.open(p, ctx));
			}).rightClick((ctx, p) -> {
				if (ctx.attribute().variable().isEmpty()) ctx.attribute().variable().remove(AttributeKeys.VARIABLES.toString());
				ctx.attribute().variable().remove(key);
				ctx.item().setAbility(ctx.index(), ctx.attribute());
				MenuManager.ABILITY_VARIABLE_LIST_MENU.open(p, ctx);
			}).build();
		}
	}
}