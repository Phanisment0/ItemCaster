package io.phanisment.itemcaster.util;

import java.util.Objects;

/**
 * This code is from source code <a href="https://maven.fabricmc.net/docs/yarn-1.21.1+build.3/net/minecraft/util/Identifier.html">Identifier</a> fabric yarn
 * but i change some to become more flexible.
 */
public final class Identifier implements Comparable<Identifier> {
	private static final int MAX_VALID_CHAR = 128;
	private static final boolean[] VALID_PATH = new boolean[MAX_VALID_CHAR];
	private static final boolean[] VALID_NAMESPACE = new boolean[MAX_VALID_CHAR];
	private static final char NAMESPACE_SEPARATOR = ':';
	private static final String DEFAULT_NAMESPACE = "minecraft";

	private final String namespace;
	private final String path;

	/**
	 * Automatically converts a valid identifier string into an Identifier.
	 * 
	 * Example:
	 *  namespace:path
	 * 
	 * @param text
	 * @throws IllegalIdentifierException if text is invalid
	 */
	public Identifier(String text) throws IllegalIdentifierException {
		this(split(text));
	}

	/**
	 * 
	 * @param namespace
	 * @param path
	 * @throws IllegalIdentifierException
	 */
	public Identifier(String namespace, String path) throws IllegalIdentifierException {
		if (namespace == null || namespace.isEmpty()) this.namespace = DEFAULT_NAMESPACE;
		else if (!validNamespace(namespace)) throw new IllegalIdentifierException("Invalid namespace: " + namespace);
		else this.namespace = namespace;

		if (!validPath(path)) throw new IllegalIdentifierException("Invalid path: " + path);
		this.path = path;
	}

	public Identifier(Identifier clone) throws IllegalIdentifierException {
		this.namespace = clone.namespace;
		this.path = clone.path;
	}

	private static Identifier split(String text) throws IllegalIdentifierException {
		if (text == null || text.isEmpty()) throw new IllegalIdentifierException("Identifier cannot be empty");

		int sparate_loc = -1;
		for (int i = 0; i < text.length(); i++) if (text.charAt(i) == NAMESPACE_SEPARATOR) {
			sparate_loc = i;
			break;
		}

		String namespace;
		String path;

		if (sparate_loc == -1) {
			namespace = DEFAULT_NAMESPACE;
			path = text;
		} else {
			namespace = sparate_loc == 0 ? DEFAULT_NAMESPACE : text.substring(0, sparate_loc);
			path = text.substring(sparate_loc + 1);
		}

		if (path.isEmpty()) throw new IllegalIdentifierException("Path cannot be empty");
		return new Identifier(namespace, path);
	}

	public static Identifier of(String identifier) {
		try {
			return new Identifier(identifier);
		} catch (IllegalIdentifierException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Identifier of(String namespace, String path) {
		try {
			return new Identifier(namespace, path);
		} catch (IllegalIdentifierException e) {
			e.printStackTrace();
			return null;
		}
	}

	public static Identifier of(Identifier identifier) {
		try {
			return new Identifier(identifier);
		} catch (IllegalIdentifierException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String namespace() {
		return this.namespace;
	}

	public String path() {
		return this.path;
	}

	public boolean empty() {
		return this.namespace == null || this.path == null;
	}

	private static boolean validPath(String text) {
		if (text == null || text.isEmpty()) return false;
		for (int i = 0; i < text.length(); i++) if (!allowedPath(text.charAt(i))) return false;
		return true;
	}

	private static boolean validNamespace(String text) {
		if (text == null || text.isEmpty()) return false;
		for (int i = 0; i < text.length(); i++) if (!allowedNamespace(text.charAt(i))) return false;
		return true;
	}

	private static boolean allowedPath(char c) {
		return c < MAX_VALID_CHAR && VALID_PATH[c];
	}

	private static boolean allowedNamespace(char c) {
		return c < MAX_VALID_CHAR && VALID_NAMESPACE[c];
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (!(o instanceof Identifier)) return false;
		var result = (Identifier)o;
		return namespace.equals(result.namespace) && path.equals(result.path);
	}

	@Override
	public int compareTo(Identifier o) {
		int result = namespace.compareTo(o.namespace);
		return result != 0 ? result : path.compareTo(o.path);
	}

	@Override
	public int hashCode() {
		return Objects.hash(namespace, path);
	}

	@Override
	public String toString() {
		return namespace + NAMESPACE_SEPARATOR + path;
	}

	static {
		for (char c = 'a'; c <= 'z'; c++) VALID_PATH[c] = true;
		for (char c = '0'; c <= '9'; c++) VALID_PATH[c] = true;
		VALID_PATH['_'] = true;
		VALID_PATH['-'] = true;
		VALID_PATH['.'] = true;
		VALID_PATH['/'] = true;
		VALID_PATH[':'] = true;

		for (char c = 'a'; c <= 'z'; c++) VALID_NAMESPACE[c] = true;
		for (char c = '0'; c <= '9'; c++) VALID_NAMESPACE[c] = true;
		VALID_NAMESPACE['_'] = true;
	}
}