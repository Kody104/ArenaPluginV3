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
		
		if (SpecialItems.getSpecialItemByDisplayName(item_stack.getItemMeta().getDisplayName()) != null) {
			SpecialItem special_item = (SpecialItem) item_stack;
			
			//Check if it's a UsableItem
			if (special_item instanceof UsableItem) {
				((UsableItem) special_item).useItem(player);
			}
		}
	}
}
