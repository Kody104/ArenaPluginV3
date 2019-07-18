package com.gmail.jpk.stu.items;

import org.bukkit.block.Chest;

/**
 * Creates a shop where ArenaPlayers can buy items. <br/>
 * The prices are based on the number of gold scraps it takes to buy the item. <br/> 
 * Conversions:
 * <ul>
 * <li>1 Golden Bar = 9 Golden Scraps</li>
 * <li>1 Shiny Emerald = 900 Golden Scraps</li>
 * <li>1 Flawless Diamond = 90000 Golden Scraps</li>
 * </ul>
 * 
 */
public class ItemShop {
	
	private Chest shop_chest;
	
	public ItemShop() {
		this.shop_chest = (Chest) SpecialItems.SHOP_CHEST.getItem();
	}
	
	public void createDefaultShop() {
		for (SpecialItems i : SpecialItems.values()) {
			if (i.getItem().getPrice() > 0) {
				shop_chest.getInventory().addItem(i.getItem());
			}
		}
	}

	public Chest getShop_chest() {
		return shop_chest;
	}

	public void setShop_chest(Chest shop_chest) {
		this.shop_chest = shop_chest;
	}
}
