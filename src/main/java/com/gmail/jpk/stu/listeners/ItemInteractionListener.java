package com.gmail.jpk.stu.listeners;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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

	public ItemInteractionListener(Plugin plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void playerBreakBlockEvent(BlockBreakEvent e) {
		Player player = e.getPlayer();
		
		if (GlobalW.getArenaPlayer(player) != null) {
			if (e.getBlock().getBlockData().getMaterial() == Material.CHEST) {
				e.setCancelled(true);
			}
		}
	}
	
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
	
	@EventHandler
	public void playerClickItemEvent(PlayerInteractEvent e) {
		Player player = e.getPlayer();
		Block block = e.getClickedBlock();
		
		//Verify Player
		if (GlobalW.getArenaPlayer(player) != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			
			//Verify Block is a Chest
			if (block != null && block.getBlockData().getMaterial() == Material.CHEST) {
				//TODO: Temp code, depending on future implementations, I may create a package for chest items.
				//Cancel the chest from opening
				e.setCancelled(true);
				Inventory basic_shop = ItemShop.getBasicShop();
				player.openInventory(basic_shop);
			}
		}
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
