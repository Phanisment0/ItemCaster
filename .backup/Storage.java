package io.phanisment.itemcaster;

import io.phanisment.itemcaster.mana_engine.ManaType;

/**
 * Hardcoded value base on code for ItemCaster.
 */
public final class Storage {
	public static boolean debugging = false;
	public static final int id_bstats = 25172;

	public static String prefix = "<gradient:#69DFFF:#5984CF>ItemCaster</gradient> | ";
	public static boolean has_craftengine = false;
	public static boolean has_itemsadder = false;
	public static boolean has_oraxen = false;
	public static boolean has_nexo = false;
	public static boolean has_papi = false;

	public static boolean has_auraskills = false;
	public static ManaType mana_engine_type = ManaType.INTERNAL;

	public static boolean hasResourcePack() {
		return has_itemsadder || has_nexo || has_oraxen || has_craftengine;
	}
}