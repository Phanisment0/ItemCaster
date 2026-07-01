package io.phanisment.itemcaster.skill;

import static io.phanisment.itemcaster.ItemCaster.core;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.inventory.ItemStack;

import io.lumine.mythic.core.utils.jnbt.CompoundTag;
import io.lumine.mythic.core.utils.jnbt.CompoundTagBuilder;
import io.lumine.mythic.core.utils.jnbt.ListTag;
import io.phanisment.itemcaster.util.ItemUtil;

public class AbilitiesContainer extends AbstractList<SkillAttribute> {
	private final List<SkillAttribute> attributes = new ArrayList<>();

	public AbilitiesContainer(List<SkillAttribute> attributes) {
		this.attributes.addAll(attributes);
	}

	@Override
	public SkillAttribute set(int index, SkillAttribute element) {
		return attributes.set(index, element);
	}

	@Override
	public void add(int index, SkillAttribute element) {
		attributes.add(index, element);
	}

	@Override
	public SkillAttribute remove(int index) {
		return attributes.remove(index);
	}

	@Override
	public int size() {
		return attributes.size();
	}

	@Override
	public SkillAttribute get(int index) {
		return attributes.get(index);
	}

	@Override
	public boolean isEmpty() {
		return attributes.isEmpty();
	}

	public ItemStack applyToItem(ItemStack item) {
		if (ItemUtil.validateItem(item)) return item;
		if (attributes.isEmpty())return item;

		List<CompoundTag> abilites_raw = new ArrayList<>();
		for (SkillAttribute attribute : attributes) abilites_raw.add(attribute.getAsCompound());
		var abilities = new ListTag(CompoundTag.class, abilites_raw);

		CompoundTag origin_item = core().getVolatileCodeHandler().getItemHandler().getNBTData(item);

		var compound_abilities = CompoundTagBuilder.create();
		compound_abilities.put(SkillActivator.ABILITIES, abilities);

		var builder = origin_item.createBuilder();
		builder.put(SkillActivator.INSTANCE, compound_abilities.build());

		return core().getVolatileCodeHandler().getItemHandler().setNBTData(item, builder.build());
	}
}