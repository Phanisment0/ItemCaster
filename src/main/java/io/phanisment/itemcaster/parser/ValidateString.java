package io.phanisment.itemcaster.parser;

/**
 * NOTE: This is used for optimization, instead using regex that can be heavy.
 */
public final class ValidateString {
	private static final int MAX_VALID_CHAR = 128;
	private static final boolean[] VALID_CHAR = new boolean[MAX_VALID_CHAR];

	public static boolean containsSpecial(String text) {
		if (text == null || text.isEmpty()) return false;
		for (int i = 0; i < text.length(); i++) if (!allowedChar(text.charAt(i))) return true;
		return false;
	}

	private static boolean allowedChar(char c) {
		return c < MAX_VALID_CHAR && VALID_CHAR[c];
	}

	static {
		for (char c = 'A'; c <= 'Z'; c++) VALID_CHAR[c] = true;
		for (char c = 'a'; c <= 'z'; c++) VALID_CHAR[c] = true;
		for (char c = '0'; c <= '9'; c++) VALID_CHAR[c] = true;
		VALID_CHAR['_'] = true;
		VALID_CHAR['.'] = true;
	}
}