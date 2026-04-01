package io.phanisment.itemcaster.util;

import java.util.Objects;

/**
 * This code is from source code <a href="https://maven.fabricmc.net/docs/yarn-1.21.1+build.3/net/minecraft/util/Identifier.html">Identifier</a> fabric yarn
 * but i change some to become more flexible.
 */
public final class Identifier implements Comparable<Identifier> {
	private static final int MAX_VALID_CHAR = 128;
	private static final boolean[] VALID_CHAR = new boolean[MAX_VALID_CHAR];
	private static final char NAMESPACE_SEPARATOR = ':';

	public final String namspace;
	public final String path;

	/**
	 * Automatic convert valid identifier text to Identifer class
	 * 
	 * Example:
	 *  namespace:path
	 * 
	 * @param text
	 * @throws IllegalIdentifierException if text is invalid
	 */
	public Identifier(String text) throws IllegalIdentifierException {
		String[] strings = split(text);
		String namespace = strings[0];
		String path = strings[1];
		if (!validText(namespace)) throw new IllegalIdentifierException("Invalid namespace: " + namespace);	
		if (!validText(path)) throw new IllegalIdentifierException("Invalid path: " + path);
		this.namspace = namespace;
		this.path = path;
	}

	/**
	 * 
	 * @param namespace
	 * @param path
	 * @throws IllegalIdentifierException
	 */
	public Identifier(String namespace, String path) throws IllegalIdentifierException {
		if (!validText(namespace)) throw new IllegalIdentifierException("Invalid namespace: " + namespace);	
		if (!validText(path)) throw new IllegalIdentifierException("Invalid path: " + path);
		this.namspace = namespace;
		this.path = path;
	}

	private String[] split(String text) throws IllegalIdentifierException {
		int i = text.indexOf(NAMESPACE_SEPARATOR);
		if (i == -1) throw new IllegalIdentifierException("Invalid Identifier text");
		String[] strings = new String[2];
		strings[0] = text.substring(0, i);
		strings[1] = text.substring(i + 1);
		return strings;
	}

	private boolean validText(String text) {
		if (text == null || text.isEmpty()) return false;
		for (int i = 0; i < text.length(); i++) if (!allowedChar(text.charAt(i))) return false;
		return true;
	}

	private boolean allowedChar(char c) {
		return c < MAX_VALID_CHAR && VALID_CHAR[c];
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Identifier)) return false;
		var that = (Identifier)o;
		return namspace.equals(that.namspace) && path.equals(that.path);
	}

	@Override
	public int compareTo(Identifier o) {
		int that = path.compareTo(o.path);
		return that != 0 ? that : namspace.compareTo(o.namspace);
	}

	@Override
	public int hashCode() {
		return Objects.hash(namspace, path);
	}

	@Override
	public String toString() {
		return namspace + NAMESPACE_SEPARATOR + path;
	}

	static {
		for (char c = 'A'; c <= 'Z'; c++) VALID_CHAR[c] = true;
		for (char c = 'a'; c <= 'z'; c++) VALID_CHAR[c] = true;
		for (char c = '0'; c <= '9'; c++) VALID_CHAR[c] = true;
		VALID_CHAR['_'] = true;
		VALID_CHAR['.'] = true;
		VALID_CHAR['/'] = true;
	}
}