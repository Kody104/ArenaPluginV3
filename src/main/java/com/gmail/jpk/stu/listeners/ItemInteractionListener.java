package com.gmail.jpk.stu.listeners;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

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
	public void entityPickupItemEvent(EntityPickupItemEvent e) {
		ItemStack stack = e.getItem().getItemStack();
		String display_name = stack.getItemMeta().getDisplayName();
		SpecialItem special_item = SpecialItems.getSpecialItemByDisplayName(display_name);
		
		if (special_item instanceof UsableItem) {
			e.setCancelled(true);
		}
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
				((UsableItem) special_item).useItem(player);
				item_stack.setAmount(0);
				player.getWorld().dropItem(player.getLocation(), special_item);
				class DespawnItemTask extends BukkitRunnable {
					public void run() {
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
						//TODO: This doesn't work. Cool.
						special_item.setAmount(0);
					}
				}
				new DespawnItemTask().runTaskLater(GlobalW.getPlugin(), 60);
			}
		}
	}
}
