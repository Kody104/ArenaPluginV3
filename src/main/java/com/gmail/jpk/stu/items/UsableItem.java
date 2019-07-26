package com.gmail.jpk.stu.items;

import org.bukkit.entity.Player;

public interface UsableItem {
	
	public static final int DEFAULT_DELAY = 60;
	
	/**
	 * Uses the item.
	 * @param player the player using the item
	 */
	public abstract void useItem(Player player);

}
