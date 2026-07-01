package io.phanisment.itemcaster.skill;

import java.util.Map;
import java.util.function.Function;

import io.lumine.mythic.core.utils.jnbt.Tag;
import io.lumine.mythic.core.utils.jnbt.ByteTag;
import io.lumine.mythic.core.utils.jnbt.CompoundTag;
import io.lumine.mythic.core.utils.jnbt.DoubleTag;
import io.lumine.mythic.core.utils.jnbt.FloatTag;
import io.lumine.mythic.core.utils.jnbt.IntTag;
import io.lumine.mythic.core.utils.jnbt.ListTag;
import io.lumine.mythic.core.utils.jnbt.StringTag;

import static io.phanisment.itemcaster.ItemCaster.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SkillAttribute {
	private static final Map<Class<?>, Function<Object, Tag>> SERIALIZERS = new HashMap<>();
	private Map<String, Object> data = new HashMap<>();

	public static SkillAttribute of(Map<String, Tag> data) {
		return new SkillAttribute(fromTagMap(data));
	}

	public SkillAttribute() {
	}

	public SkillAttribute(CompoundTag data) {
		this(fromTagMap(data.getValue()));
	}

	public SkillAttribute(Map<String, Object> data) {
		this.data = data;
	}

	public <T> SkillAttribute set(Key key, Class<T> type, T value) {
		this.data.put(key.toString(), value);
		return this;
	}

	public <T> T get(Key key, Class<T> type, T def) {
		Object value = data.get(key.toString());
		if (value == null) return def;
		return type.isInstance(value) ? type.cast(value) : def;
	}

	public boolean has(Key key) {
		return data.containsKey(key.toString());
	}

	public Map<String, Object> getAsMap() {
		return data;
	}

	public Map<String, Tag> gatAsMapTag() {
		Map<String, Tag> tags = new HashMap<>();
		for (var entry : data.entrySet()) {
			Tag tag = toTag(entry.getValue());
			if (tag != null) tags.put(entry.getKey(), tag);
		}
		return tags;
	}

	public CompoundTag getAsCompound() {
		Map<String, Tag> tags = new HashMap<>();
		for (var entry : data.entrySet()) {
			Tag tag = toTag(entry.getValue());
			if (tag != null) tags.put(entry.getKey(), tag);
		}
		return core().getVolatileCodeHandler().createCompoundTag(tags);
	}

	private static Tag toTag(Object value) {
		if (value == null) return null;
		var serializer = findSerializer(value);
		return serializer == null ? null : serializer.apply(value);
	}

	private static Function<Object, Tag> findSerializer(Object value) {
		Class<?> clazz = value.getClass();
		for (var entry : SERIALIZERS.entrySet()) if (entry.getKey().isAssignableFrom(clazz)) return entry.getValue();
		return null;
	}

	private static Object fromTag(Tag tag) {
		if (tag instanceof StringTag stringTag) return stringTag.getValue();
		if (tag instanceof IntTag intTag) return intTag.getValue();
		if (tag instanceof FloatTag floatTag) return floatTag.getValue();
		if (tag instanceof DoubleTag doubleTag) return doubleTag.getValue();
		if (tag instanceof ByteTag byteTag) return byteTag.getValue();
		if (tag instanceof CompoundTag compoundTag) return fromTagMap(compoundTag.getValue());
		if (tag instanceof ListTag listTag) return fromTagList(listTag);
		return null;
	}

	private static Map<String, Object> fromTagMap(Map<String, Tag> tags) {
		Map<String, Object> result = new HashMap<>();
		for (var entry : tags.entrySet()) {
			Object value = fromTag(entry.getValue());
			if (value != null) result.put(entry.getKey(), value);
		}
		return result;
	}

	private static List<Object> fromTagList(ListTag listTag) {
		List<Object> result = new ArrayList<>();
		for (Tag tag : listTag.getValue()) {
			Object value = fromTag(tag);
			if (value != null) {
				result.add(value);
			}
		}
		return result;
	}

	@Override
	public String toString() {
		return data.toString();
	}

	public static enum Key {
		SKILL,
		ACTIVATOR,
		POWER,
		COOLDOWN,
		SNEAKING,
		SIGNAL,
		VARIABLES,
		CANCEL_EVENT,
		SHOW_COOLDOWN;

		@Override
		public String toString() {
			return name().toLowerCase();
		}
	}

	static {
		SERIALIZERS.put(Byte.class, v -> new ByteTag((Byte)v));
		SERIALIZERS.put(String.class, v -> new StringTag((String)v));
		SERIALIZERS.put(Integer.class, v -> new IntTag((Integer)v));
		SERIALIZERS.put(Float.class, v -> new FloatTag((Float)v));
		SERIALIZERS.put(Double.class, v -> new DoubleTag((Double)v));
		SERIALIZERS.put(Boolean.class, v -> new ByteTag((byte)((Boolean)v ? 1 : 0)));
		SERIALIZERS.put(Map.class, v -> {
			Map<String, Tag> tags = new HashMap<>();
			Map<?, ?> map = (Map<?, ?>)v;
			for (var entry : map.entrySet()) {
				String key = String.valueOf(entry.getKey());
				Tag tag = toTag(entry.getValue());
				if (tag != null) tags.put(key, tag);
			}
			return core().getVolatileCodeHandler().createCompoundTag(tags);
		});
		SERIALIZERS.put(List.class, value -> {
			List<Tag> tags = new ArrayList<>();
			Class<? extends Tag> type = null;
			for (Object element : (List<?>) value) {
				Tag tag = toTag(element);
				if (tag == null) continue;
				if (type == null) type = tag.getClass();
				else if (!type.equals(tag.getClass())) throw new IllegalArgumentException("NBT List must contain only one tag type. Found " + type.getSimpleName() + " and " + tag.getClass().getSimpleName());
				tags.add(tag);
			}
			if (type == null) type = StringTag.class;
			return new ListTag(type, tags);
		});
	}
}