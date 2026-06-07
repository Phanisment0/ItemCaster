package io.phanisment.itemcaster.registry;

import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.skills.placeholders.Placeholder;
import io.lumine.mythic.core.skills.placeholders.PlaceholderExecutor;
import io.lumine.mythic.core.utils.annotations.AnnotationUtil;
import io.lumine.mythic.core.utils.annotations.MythicPlaceholder;
import io.phanisment.itemcaster.ItemCaster;

public class MythicPlaceholderRegistery {
	@SuppressWarnings("unchecked")
	public static void register() {
		PlaceholderExecutor placeholder = ItemCaster.core().getPlaceholderManager();
		for (Class<?> clazz : AnnotationUtil.getAnnotatedClasses(ItemCaster.inst(), "io.phanisment.itemcaster.skill.placeholder", MythicPlaceholder.class)) {
			try {
				MythicPlaceholder anno = clazz.getAnnotation(MythicPlaceholder.class);
				String name = anno.placeholder();
				if (!Placeholder.class.isAssignableFrom(clazz)) continue;
				placeholder.register(name, (Class<Placeholder>)clazz);
				for (String alias : anno.aliases()) placeholder.register(alias, (Class<Placeholder>)clazz);
			} catch (Exception ex) {
				MythicLogger.error("Failed to load placeholder {0}", clazz.getCanonicalName());
			}
		}
	}
}
