package com.gmail.jpk.stu.items;

import org.bukkit.entity.Player;

public interface UsableItem {
	
	/**
	 * Uses the item.
	 * @param player the player using the item
	 */
	public abstract void useItem(Player player);

}
