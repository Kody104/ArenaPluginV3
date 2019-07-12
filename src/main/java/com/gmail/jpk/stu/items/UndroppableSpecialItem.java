package com.gmail.jpk.stu.items;

import org.bukkit.Material;

/**
 * <b>An UndroppableSpecialItem is a SpecialItem that can not be dropped from an ArenaPlayer's inventory.</b>
 * @author ASquanchyPenguin
 */
public abstract class UndroppableSpecialItem extends SpecialItem {

	/**
	 * <b>Creates an UndroppableSpecialItem.</b>
	 * @param material the Material which represents the UndroppableSpecialItem
	 * @param display_name the name of the UndroppableSpecialItem
	 * @param lore the lore of the UndroppableSpecialItem
	 */
	public UndroppableSpecialItem(Material material, int UID, String display_name, String[] lore) {
		super(material, UID, display_name, lore);
	}
}
