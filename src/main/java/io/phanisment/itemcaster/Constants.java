package io.phanisment.itemcaster;

/**
 * Configuration base on code for ItemCaster.
 */
public final class Constants {
	public static final boolean debugging = true;
	public static final int id_bstats = 25172;
	
	public static String prefix = "<gradient:#69DFFF:#5984CF>ItemCaster</gradient> | ";
	public static boolean hasItemsAdder = false;
	public static boolean hasNexo = false;
	public static boolean hasOraxen = false;
	public static boolean hasPAPI = false;
	
	public static boolean hasResourcePack() {
		return hasItemsAdder || hasNexo || hasOraxen;
	}
}