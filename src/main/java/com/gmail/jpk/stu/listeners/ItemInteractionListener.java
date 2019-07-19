package com.gmail.jpk.stu.listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.items.SpecialItem;
import com.gmail.jpk.stu.items.SpecialItems;
import com.gmail.jpk.stu.items.UsableItem;

/**
 * Handles players' interactions with items.
 *
 */
public class ItemInteractionListener extends BasicListener {

	public ItemInteractionListener(Plugin plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void playerBreakBlockEvent(BlockBreakEvent e) {
		
	}
	
	@EventHandler
	public void playerDropItemEvent(PlayerDropItemEvent e) {
		Player player = e.getPlayer();
		
		if (GlobalW.getArenaPlayer(player) == null) {
			return;
		}
		
		//Get the item, verify its a SpecialItem
		ItemStack item_stack = e.getItemDrop().getItemStack();
		String display_name = item_stack.getItemMeta().getDisplayName();
		
		if (SpecialItems.getSpecialItemByDisplayName(display_name) != null) {
			SpecialItem special_item = SpecialItems.getSpecialItemByDisplayName(display_name);
			
			//Check if it's a UsableItem
			if (special_item instanceof UsableItem) {
				e.setCancelled(true);
				((UsableItem) special_item).useItem(player);
			}
		}
	}
}
