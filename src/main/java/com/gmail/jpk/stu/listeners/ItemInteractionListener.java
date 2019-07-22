package com.gmail.jpk.stu.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.commands.BasicCommand;
import com.gmail.jpk.stu.items.ItemShop;
import com.gmail.jpk.stu.items.SpecialItem;
import com.gmail.jpk.stu.items.SpecialItems;
import com.gmail.jpk.stu.items.UsableItem;

/**
 * Handles players' interactions with items.
 *
 */
public class ItemInteractionListener extends BasicListener {
	
	//A list of SpecialItems dropped on the ground.
	private static List<SpecialItem> dropped_items = new ArrayList<SpecialItem>();

	public ItemInteractionListener(Plugin plugin) {
		super(plugin);
	}
	
	/**
	 * Adds a SpecialItem to the list.
	 * @param item the item to add.
	 */
	public static void addDroppedItem(SpecialItem item) {
		dropped_items.add(item);
	}
	
	/**
	 * Clears all the SpecialItems on the ground.
	 */
	public static void clearDroppedItems() {
		for (SpecialItem item : dropped_items) {
			if (item != null && item.getAmount() > 0) {
				item.setAmount(0);
			}
		}
		
		dropped_items.clear();
	}
	
	/**
	 * Gets the SpecialItems that have been dropped on the Ground.
	 * @return the list (NOTE: some elements may be null).
	 */
	public static List<SpecialItem> getDroppedItems() {
		return dropped_items;
	}
	
	
	/**
	 * Handles when a Player Breaks a Block. Critical for Arena's Shop.
	 * @param e the event
	 */
	@EventHandler
	public void playerBreakBlockEvent(BlockBreakEvent e) {
		Player player = e.getPlayer();
		
		if (GlobalW.getArenaPlayer(player) != null) {
			if (e.getBlock().getBlockData().getMaterial() == Material.CHEST) {
				e.setCancelled(true);
			}
		}
	}
	
	/**
	 * Handles when a Player clicks on an item in an inventory. It is the core of the Arena's Shop.
	 * @param e the event
	 */
	@EventHandler
	public void playerClickInventoryEvent(InventoryClickEvent e) {
		Inventory inventory = e.getClickedInventory();
		
		//Don't remove items from the chest
		if (inventory == ItemShop.getBasicShop()) {
			e.setCancelled(true);
			
			ItemStack stack = e.getCurrentItem();
			
			//Player didn't click on anything
			if (stack == null) {
				return;
			}
			
			String display_name = stack.getItemMeta().getDisplayName();
			SpecialItem item = SpecialItems.getSpecialItemByDisplayName(display_name);
			HumanEntity human_entity = e.getWhoClicked();
			
			if (item != null && human_entity instanceof Player) {
				Player player = (Player) human_entity;
				ArenaPlayer arena_player = GlobalW.getArenaPlayer(player);
				
				if (arena_player != null) {
					if (arena_player.removeMoney(item.getPrice())) {
						BasicCommand.executeCommand(String.format("gci %d %s 1", item.getUID(), player.getName()));
					} else {
						GlobalW.toPlayerError(player, "You don't have enough money!");
					}
				}
			}
		}
	}
	
	/**
	 * Handles when a Player interactions with blocks.
	 * @param e the event
	 */
	@EventHandler
	public void playerClickItemEvent(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		
		//Verify Player
		if (GlobalW.getArenaPlayer(player) != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			//Verify Block is a Chest
			if (block != null && block.getBlockData().getMaterial() == Material.CHEST) {
				//TODO: Add branches if more clickable items are added.
				//Cancel the chest from opening
				e.setCancelled(true);
				Inventory basic_shop = ItemShop.getBasicShop();
				player.openInventory(basic_shop);
			}
		}
	}
	
	@EventHandler
	public void playerConsumeItem(PlayerItemConsumeEvent e) {
		Player player = e.getPlayer();
		ItemStack item = e.getItem();
		SpecialItem spec_item = SpecialItems.getSpecialItemByDisplayName(item.getItemMeta().getDisplayName());
		
		if (GlobalW.getArenaPlayer(player) != null && spec_item != null) {
			//TODO: Add branches if more consumable items are added
			if (spec_item instanceof UsableItem) {
				int amount = item.getAmount();
				amount = (amount == 1) ? (0) : (amount--);

				e.setCancelled(true);
				item.setAmount(amount);
				
				((UsableItem) spec_item).useItem(player);
			} 
		}
	}
	
	/**
	 * Handles when a Player tries to craft an Item. At present it stops all Arena Players from crafting. <br/>
	 * However, it is likely that certain recipes will be permitted in the future.
	 * @param e the event
	 */
	@EventHandler
	public void playerCraftItemEvent(CraftItemEvent e) {
		//Verify the Humanentity is a Player
		HumanEntity human_entity = e.getWhoClicked();
		
		if (!(human_entity instanceof Player)) {
			return;
		}
		
		//Verify Player is an ArenaPlayer
		Player player = (Player) human_entity;
		
		if (GlobalW.getArenaPlayer(player) != null) {
			//Cancel the event (Except for recipes that we'll allow, but there aren't any that I know yet.)
			e.setCancelled(true);
		}
	}
	
	/**
	 * Handles when an Entity picks up an item. Critical to prevent Arena Players from picking up UsableItems.
	 * @param e the event
	 */
	@EventHandler
	public void entityPickupItemEvent(EntityPickupItemEvent e) {
		ItemStack stack = e.getItem().getItemStack();
		String display_name = stack.getItemMeta().getDisplayName();
		SpecialItem special_item = SpecialItems.getSpecialItemByDisplayName(display_name);
		
		if (special_item instanceof UsableItem) {
			e.setCancelled(true);
		}
	}
	
	/**
	 * Handles when a Player drops an item. Critical for UsableItems.
	 * @param e
	 */
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
				//Use the Item
				((UsableItem) special_item).useItem(player);
				
				//Add it to the Drop List
				addDroppedItem(special_item);
				
				//Schedule the Item to Despawn
				//NOTE: After much pain, having the class here is the only way to achieve the desired effect.
				class DespawnItemTask extends BukkitRunnable {
					public void run() {
						player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1f, 1f);
						item_stack.setAmount(0);
					}
				}
				new DespawnItemTask().runTaskLater(GlobalW.getPlugin(), 60);
			}
		}
	}
}
