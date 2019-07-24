package com.gmail.jpk.stu.listeners;

import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.items.UndroppableSpecialItem;

/**
 * Prevents an UndroppableSpecialItem from being dropped.
 * @deprecated Use <ItemInteractionListener> isntead.
 */
public class UndroppableSpecialItemListener extends BasicListener {

	/**
	 * Listens for when an UndroppableSpecialItem is dropped.
	 * @param plugin
	 */
	public UndroppableSpecialItemListener(Plugin plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void onItemDrop(PlayerDropItemEvent e) {
		Item drop = e.getItemDrop();
		ItemStack stack = drop.getItemStack();
		
		//TODO: I don't think this will work, but we'll try it.
		if (stack instanceof UndroppableSpecialItem) { // This won't work - Jerome
			/*
			 * Try this instead
			 * if(stack.hasItemMeta()) {
			 *   if(stack.getItemMeta().getLore().get(0).equalsIgnoreCase("special item")) {
			 *     e.setCancelled(true);
			 *   }
			 * }
			 */
			Player player = (e.getPlayer());
			player.sendMessage("This item can't be dropped!");
			e.setCancelled(true);
		}
	}
}
