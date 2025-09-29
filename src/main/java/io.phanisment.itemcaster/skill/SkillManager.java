package io.phanisment.itemcaster.skill;

import io.lumine.mythic.core.skills.SkillExecutor;
import io.lumine.mythic.core.skills.SkillCondition;
import io.lumine.mythic.core.skills.SkillMechanic;
import io.lumine.mythic.core.logging.MythicLogger;
import io.lumine.mythic.core.utils.annotations.AnnotationUtil;
import io.lumine.mythic.core.utils.annotations.MythicCondition;
import io.lumine.mythic.core.utils.annotations.MythicMechanic;

import io.phanisment.itemcaster.ItemCaster;

import java.util.Map;
import java.util.Collection;
import java.lang.reflect.Field;

public class SkillManager {
	public static void register() {
		// Condition Registry
		Collection<Class<?>> class_conditions = AnnotationUtil.getAnnotatedClasses(ItemCaster.inst(), "io.phanisment.itemcaster.skill.condition", MythicCondition.class);
		for (Class<?> condition : class_conditions) {
			try {
				String name = condition.getAnnotation(MythicCondition.class).name();
				String[] aliases = condition.getAnnotation(MythicCondition.class).aliases();
				if (!SkillCondition.class.isAssignableFrom(condition)) continue;
				getConditions().put(name.toUpperCase(), (Class<? extends SkillCondition>)condition);
				for (String alias : aliases) {
					getConditions().put(alias.toUpperCase(), (Class<? extends SkillCondition>)condition);
				}
			} catch (Exception e) {
				MythicLogger.error("Failed to load condition {0}", condition.getCanonicalName());
			}
		}
		
		// Mechanic Registry
		Collection<Class<?>> class_mechanics = AnnotationUtil.getAnnotatedClasses(ItemCaster.inst(), "io.phanisment.itemcaster.skill.mechanic", MythicMechanic.class);
		for (Class<?> mechanic : class_mechanics) {
			try {
				String name = mechanic.getAnnotation(MythicMechanic.class).name();
				String[] aliases = mechanic.getAnnotation(MythicMechanic.class).aliases();
				if (!SkillMechanic.class.isAssignableFrom(mechanic)) continue;
				getMechanics().put(name.toUpperCase(), (Class<? extends SkillMechanic>)mechanic);
				for (String alias : aliases) {
					getMechanics().put(alias.toUpperCase(), (Class<? extends SkillMechanic>)mechanic);
				}
			} catch (Exception e) {
				MythicLogger.error("Failed to load mechanic {0}", mechanic.getCanonicalName());
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Class<? extends SkillCondition>> getConditions() {
		try {
			Field map = SkillExecutor.class.getDeclaredField("CONDITIONS");
			map.setAccessible(true);
			return (Map<String, Class<? extends SkillCondition>>)map.get(null);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static Map<String, Class<? extends SkillMechanic>> getMechanics() {
		try {
			Field map = SkillExecutor.class.getDeclaredField("MECHANICS");
			map.setAccessible(true);
			return (Map<String, Class<? extends SkillMechanic>>)map.get(null);
		} catch (IllegalAccessException | NoSuchFieldException e) {
			e.printStackTrace();
		}
		return null;
	}
}