package io.phanisment.itemcaster.menu.ability;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.google.common.collect.Lists;

import io.lumine.mythic.bukkit.utils.menu.EditableMenuBuilder;
import io.lumine.mythic.bukkit.utils.menu.Icon;
import io.lumine.mythic.bukkit.utils.menu.IconBuilder;
import io.lumine.mythic.bukkit.utils.menu.MenuData;
import io.phanisment.itemcaster.menu.CasterMenu;
import io.phanisment.itemcaster.menu.MenuManager;
import io.phanisment.itemcaster.skill.SkillAttribute;
import io.phanisment.itemcaster.util.CasterColorCode;

public class ItemAbilitiesMenu extends CasterMenu<ItemAbilitiesMenuContext> {
	public ItemAbilitiesMenu() {
		super("menus/item-abilities.yml");
	}

	@Override
	public void open(Player p, ItemAbilitiesMenuContext ctx) {
		List<IndexAttribute> list = Lists.newArrayList();
		List<SkillAttribute> attributes = ctx.item().getAbilities();
		for (int i = 0; i < attributes.size(); i++) list.add(new IndexAttribute(i, attributes.get(i)));
		this.open(p, ctx, "Edit: " + ctx.item().getId(), list);
	}

	@Override
	public EditableMenuBuilder<ItemAbilitiesMenuContext> build(EditableMenuBuilder<ItemAbilitiesMenuContext> builder) {
		builder = this.addPageButtons(builder);
		builder.getIcon("BACK_BUTTON").ifPresent(i -> i.getBuilder().click((ctx, p) -> ctx.openPrevious(p)));
		builder.getIcon("GIVE_BUTTON").ifPresent(icon -> icon.getBuilder().click((ctx, p) -> p.getInventory().addItem(ctx.item().getItemStack())));
		builder.getIcon("CREATE_BUTTON").ifPresent(i -> i.getBuilder().click((ctx, p) -> {
			ctx.item().addAbility(SkillAttribute.empty());
			ctx.open(p);
		}));
		builder.getIcon("ITEM_BUTTON").ifPresent(i -> i.getBuilder().itemStack(ctx -> ctx.item().getItemStack()).hideFlags(false));
		return builder;
	}

	public static record IndexAttribute(int index, SkillAttribute attribute) implements MenuData<ItemAbilitiesMenuContext> {
		@Override
		public Icon<ItemAbilitiesMenuContext> getIcon() {
			return IconBuilder.<ItemAbilitiesMenuContext>create().material(Material.BLAZE_POWDER).hideFlags().name(CasterColorCode.BLUE + attribute.skill() + "<white>: " + attribute.activator()).lore(List.of(
				"",
				CasterColorCode.ORANGE + "✎ <gray>L - Edit Ability",
				CasterColorCode.MAGENTA + "☈ <gray>R - Remove"
			)).click((ctx, p) -> {
				MenuManager.ITEM_ABILITY.open(p, new ItemAbilityMenuContext(ctx.item(), ctx, attribute, index));
			}).rightClick((ctx, p) -> {
				ctx.item().removeAbility(index);
				ctx.open(p);
			}).build();
		}
	}
}