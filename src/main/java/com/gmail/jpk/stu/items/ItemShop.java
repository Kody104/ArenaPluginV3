package com.gmail.jpk.stu.items;

import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class ItemShop {

	//static so it's universal and we can access it directly in events
	private static Inventory basic_shop;
	
	static 
	{
		basic_shop = Bukkit.createInventory(null, 36, "Arena Basic Market");
		basic_shop.setItem(0, SpecialItems.getSpecialItemByUID(SpecialItems.BOOM_STICK.getUID()));
		basic_shop.setItem(1, SpecialItems.getSpecialItemByUID(SpecialItems.PANIC_POWDER.getUID()));
		basic_shop.setItem(18, SpecialItems.getSpecialItemByUID(SpecialItems.GOLDEN_BAR.getUID()));
		basic_shop.setItem(19, SpecialItems.getSpecialItemByUID(SpecialItems.CHIPPED_EMERALD.getUID()));
		basic_shop.setItem(20, SpecialItems.getSpecialItemByUID(SpecialItems.FLAWLESS_DIAMOND.getUID()));
	}
	
	public static Inventory getBasicShop() {
		return basic_shop;
	}
}
