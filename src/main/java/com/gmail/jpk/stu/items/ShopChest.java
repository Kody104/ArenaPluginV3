package com.gmail.jpk.stu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;

public class ShopChest extends SpecialItem {
	
	//This Chest's Inventory
	private List<SpecialItem> inventory;
	
	//The ShopChestMode (which determines its default inventory)
	private ShopChestModes mode;
	
	/**
	 * Creates a ShopChest with a set inventory
	 * @param inventory the items to add to the chest
	 */
	public ShopChest(ShopChestModes mode) {
		super(Material.CHEST, SpecialItems.SHOP_CHEST.getUID(), SpecialItems.SHOP_CHEST.getDisplayName());
		
		setMode(mode);
		setInventory(new ArrayList<SpecialItem>());
		
		for (SpecialItem item : ShopChestModes.BASIC.getItems()) {
			inventory.add(item);
		}
		
		updateInventory();
	}
	
	/**
	 * Creates a ShopChest with a basic inventory.
	 */
	public ShopChest() {
		this(ShopChestModes.BASIC);
	}
	
	/**
	 * Buys an item from the chest if the Player has enough money.
	 * @param player the player who wants to purchase an item
	 * @param special_item the SpecialItem they wish to purchase
	 * @return the SpecialItem if the transaction is successful, otherwise null
	 */
	public SpecialItem buyItem(ArenaPlayer player, SpecialItem special_item) {
		int player_currency = player.getMoney();
		int spec_item_price = special_item.getPrice();
		
		if (spec_item_price <= player_currency) {
			toPlayer(player.getmPlayer(), ChatColor.GREEN + "Thank you for your purchase!");
			player.removeMoney(spec_item_price);
			return SpecialItems.getSpecialItemByUID(special_item.getUID());
		} 
		
		toPlayer(player.getmPlayer(), ChatColor.RED + "Transaction failed! You don't have enough money.");
		
		return null;
	}
	
	private void toPlayer(Player player, String message) {
		player.sendMessage("<Arena-Shop> " + message);
	}
	
	/**
	 * Updates the ShopChest's Inventory
	 */
	public void updateInventory() {
		for (SpecialItem item : inventory) {
			((Chest) this).getInventory().addItem(item);
		}
	}
	

	public List<SpecialItem> getInventory() {
		return inventory;
	}

	public void setInventory(List<SpecialItem> inventory) {
		this.inventory = inventory;
	}

	public ShopChestModes getMode() {
		return mode;
	}

	public void setMode(ShopChestModes mode) {
		this.mode = mode;
	}
	
}
