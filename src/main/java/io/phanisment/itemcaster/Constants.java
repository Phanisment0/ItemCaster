package io.phanisment.itemcaster;

/**
 * Configuration base on code for ItemCaster.
 */
public final class Constants {
	public static final boolean debugging = true;
	public static final int id_bstats = 25172;

	public static String prefix = "<gradient:#69DFFF:#5984CF>ItemCaster</gradient> | ";
	public static boolean has_craftengine = false;
	public static boolean has_itemsadder = false;
	public static boolean has_oraxen = false;
	public static boolean has_nexo = false;
	public static boolean has_papi = false;

	public static boolean hasResourcePack() {
		return has_itemsadder || has_nexo || has_oraxen || has_craftengine;
	}
}