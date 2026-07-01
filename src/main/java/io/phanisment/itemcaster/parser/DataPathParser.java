package io.phanisment.itemcaster.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.phanisment.itemcaster.skill.SkillAttribute;

public class DataPathParser {
	private static final Map<String, String[]> PATH_CACHE = new ConcurrentHashMap<>();

	@SuppressWarnings("unlikely-arg-type" )
	public static <T> T parse(Object source, String path) {
		if (source == null || path == null || path.isEmpty()) return null;
		String[] tokens = PATH_CACHE.computeIfAbsent(path, DataPathParser::split);
		
		Object current = source;
		for (String token : tokens) {
			if (current instanceof Map map) current = map.get(token);
			else if (current instanceof List list) current = getFromList(list, token);
			else if (current instanceof SkillAttribute attribute) current = attribute.getAsMap().get(tokens);
			else return null;
			if (current == null) return null;
		}
		return (T)current;
	}

	private static String[] split(String path) {
		List<String> tokens = new ArrayList<>();
		int start = 0;
		int len = path.length();

		while (start < len) {
			int i = path.indexOf('.', start);
			if (i == -1) {
				tokens.add(path.substring(start));
				break;
			}
			tokens.add(path.substring(start, i));
			start = i + 1;
		}

		return tokens.toArray(new String[0]);
	}

	private static Object getFromList(List<?> list, String token) {
		if (token.isEmpty()) return null;
		int index = 0;
		for (int i = 0; i < token.length(); i++) {
			char c = token.charAt(i);
			if (c < '0' || c > '9') return null;
			index = index * 10 + (c - '0');
		}
		return (index < list.size()) ? list.get(index) : null;
	}
}