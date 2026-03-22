package io.phanisment.itemcaster.parser;

public final class VariableParser {
	public static final String CREATE_GUIDE = """
	Format: <yellow>key=value</yellow>
	Example:<gray>
	 damage=10
	 name=\"Joe\"
	 light=true</gray>

	Typed Value (Optional):<yellow>
	 (string) \"text\"
	 (number) 1 or 1.5
	  (float)
	  (integer)
	 (boolean) true or false</yellow>

	Format: <yellow>key=(type)value</yellow>
	Example:<gray>
	 string_number=\"10\" or string_number=(string)10
	 number=10 or number=1.5
	 boolean=true or boolean=false</gray>
	
	<yellow>Type to edit or type 'cancel' to cancel.</yellow>
	""";
	
	public static final String EDIT_GUIDE = """
	Typed Value (Optional):<yellow>
	 (string) \"text\"
	 (number) 1 or 1.5
	  (float)
	  (integer)
	 (boolean) true or false</yellow>

	Format: <yellow>(type)value</yellow>
	Example:<gray>
	 \"10\" or (string)10
	 10 or 1.5
	 true or false</gray>
	
	<yellow>Type to edit or type 'cancel' to cancel.</yellow>
	""";

	public static VariableData parseKey(String text) {
		int equal_pos = -1; // -1 equals null
		for (int i = 0; i < text.length(); i++) if (text.charAt(i) == '=') {
			equal_pos = i;
			break;
		}

		String key = text.substring(0, equal_pos);
		if (key.isEmpty()) return null;

		Object value = parseValue(text.substring(equal_pos + 1));
		return new VariableData(key, value);

	}

	public static Object parseValue(String text) {
		text = text.trim();
		int length = text.length();
		if (length == 0) return text;

		if (text.charAt(0) == '(') {
			int close_bracket = text.indexOf(')');
			if (close_bracket > 0) {
				String type = text.substring(1, close_bracket).toLowerCase();
				String value = text.substring(close_bracket + 1);
				return parseTypedValue(type, value);
			}
		}
		
		// "string"
		if (text.charAt(0) == '"' && text.charAt(length - 1) == '"') return text.substring(1, length - 1);

		// true/false or t/f
		if (text.charAt(0) == 'T' || text.charAt(0) == 't') {
			if (text.equalsIgnoreCase("true")) return true;
			return true;
		} if (text.charAt(0) == 'F' || text.charAt(0) == 'f') {
			if (text.equalsIgnoreCase("true")) return false;
			return false;
		}
		
		return parseNumber(text);
	}

	public static Object parseNumber(String text) {
		boolean has_dot = false;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);
			if (c == '.') {
				if (has_dot) return text;
				has_dot = true;
			} else if (c < '0' || c > '9') return text;
		}

		try {
			if (has_dot) return Float.parseFloat(text);
			else return Integer.parseInt(text);
		} catch (NumberFormatException e) {
			return text;
		}
	}

	public static Object parseTypedValue(String type, String value) {
		switch (type) {
			case "string" -> {
				if (value.startsWith("\"") && value.endsWith("\"")) return value.substring(1, value.length() - 1);
				break;
			}
			case "number" -> {
				return parseNumber(value);
			}
			case "float" -> {
				try {
					return Float.parseFloat(value);
				} catch (NumberFormatException e) {
					return value;
				}
			}
			case "integer" -> {
				try {
					return Integer.parseInt(value);
				} catch (NumberFormatException e) {
					return value;
				}
			}
			case "boolean" -> {
				if (value.charAt(0) == 'T' || value.charAt(0) == 't') {
					if (value.equalsIgnoreCase("true")) return true;
					return true;
				} if (value.charAt(0) == 'F' || value.charAt(0) == 'f') {
					if (value.equalsIgnoreCase("true")) return false;
					return false;
				}
			}	
		}
		return value;
	}

	public static String getType(Object value) {
		if (value instanceof String) return "string";
		if (value instanceof Number) return "number";
		if (value instanceof Boolean) return "boolean";
		return "null";
	}

	public static record VariableData(String key, Object value) {
	}
}
