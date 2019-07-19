package com.gmail.jpk.stu.listeners;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.items.SpecialItem;
import com.gmail.jpk.stu.items.SpecialItems;

/**
 * Handles players' interactions with items.
 *
 */
public class ItemInteractionListener extends BasicListener {

	public ItemInteractionListener(Plugin plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void playerBreakItemEvent(BlockBreakEvent e) {
		//Verify this player is in the arena
		Player player = e.getPlayer();
		
		if (GlobalW.getArenaPlayer(player) == null) {
			return;
		}
		
		//Get all special items involved
		Collection<ItemStack> blocks = e.getBlock().getDrops();
		ArrayList<SpecialItem> special_items = new ArrayList<SpecialItem>();
		
		for (ItemStack stack : blocks) {
			SpecialItem item = SpecialItems.getSpecialItemByDisplayName(stack.getItemMeta().getDisplayName());
			
			if (item != null) {
				special_items.add(item);
			}
		}
		
		//Handle Special Items
		for (SpecialItem i : special_items) {
			SpecialItems used_item = SpecialItems.getSpecialItemEnumByDisplayName(i.getDisplayName());
			
			if (used_item != null) {
				used_item.useItem(player);
			}
		}
		
	}
}
