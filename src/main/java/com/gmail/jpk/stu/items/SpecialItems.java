package com.gmail.jpk.stu.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.tasks.DelayedExplosionTask;

public enum SpecialItems {
	
	/* 
	 * NOTE: Enum values are organized by UID.
	 * UIDs are assigned as follows:
	 * 		[100] is reserved for the test dummy stick
	 * (100, 170) is used for common items that could be bought in the shop or earned in the arena
	 * [170, 180) is used for items not meant to be carried by the player
	 * [180, 199] is used for items that can only be dropped by BOSSES
	 * 
	 */
	TEST_DUMMY_STICK	(new SpecialItem(Material.STICK, 				100, "Test Dummy Stick", 	new String[] { "This item is intended to be used as a debugging tool." }) ),
	
	GOLDEN_SCRAP		(new SpecialItem(Material.GOLD_NUGGET, 			101, "Golden Scrap", 		new String[] { "Don't let its color fool you - it's not even worth its weight." }) ),
	GOLDEN_BAR			(new SpecialItem(Material.GOLD_INGOT, 			102, "Golden Bar", 			new String[] { "A compact way to store your golden scraps." })),
	SHINY_EMERALD		(new SpecialItem(Material.EMERALD, 		900, 	103, "Shiny Emerald", 		new String[] { "A shiny gem with a modest value." })),
	FLAWLESS_DIAMOND	(new SpecialItem(Material.DIAMOND, 		90000,	104, "Flawless Diamond", 	new String[] { "An excellent item to flaunt your wealth at others." })),
	BOOM_STICK			(new SpecialItem(Material.STICK, 		100,	105, "Boom Stick", 			new String[] { "Explodes when left behind." })),
	JUICY_APPLE			(new SpecialItem(Material.GOLDEN_APPLE, 9000,	107, "Juicy Apple",			new String[] { "A delicious fruit that heals you slowly overtime." })),
	PANIC_POWDER		(new SpecialItem(Material.BLAZE_POWDER, 36000,	108, "Panic Powder", 		new String[] { "Causes enemies to attack each other when left behind." })),

	SHOP_CHEST			(new SpecialItem(Material.CHEST, 				170, "Shop Chest")),
	
	SPIKY_CACTUS		(new SpecialItem(Material.CACTUS, 				180, "Spiky Cactus", 		new String[] { "Owning this item increases the amount of damage you take." })),
	;
	
	private final SpecialItem SPECIAL_ITEM;
	
	private SpecialItems(SpecialItem special_item) {
		this.SPECIAL_ITEM = special_item;
	}
	
	/**
	 * Activates the items ability - if any.
	 * @param Player the player who used the item
	 */
	public void useItem(Player player) {
		switch (this) {
			case TEST_DUMMY_STICK:
			{
				player.sendMessage("What is my purpose?");
			}
			
			case BOOM_STICK:
			{
				new DelayedExplosionTask(player).runTaskLater(GlobalW.getPlugin(), 3000);
			} 
			
			default:
			{
				return;
			}
		}
	}
	
	/**
	 * Gets a SpecialItem by its unique id. Returns NULL if none was found.
	 * @param uid the uid of the SpecialItem
	 * @return the SpecialItem or NULL if not found.
	 */
	public static SpecialItem getSpecialItemByUID(int uid) {
		//TODO: Add in SpecialItems as they are created
		for (SpecialItems i : SpecialItems.values()) {
			if (i.getItem().getUID() == uid) {
				return i.getItem();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the UID of the SpecialItem by its display name
	 * @param display_name the SpecialItem's display name
	 * @return the UID of the SpecialItem or -1 if not found
	 */
	public static int getUIDByDisplayName(String display_name) {
		for (SpecialItems i : SpecialItems.values()) {
			if (i.getItem().getDisplayName().equalsIgnoreCase(display_name)) {
				return i.getItem().getUID();
			}
		}
		
		return -1;
	}
	
	/**
	 * Gets a SpecialItem by its display name. Returns NULL if none was found.
	 * @param display_name the display name of the SpecialItem
	 * @return the SpecialItem or NULL if not found.
	 */
	public static SpecialItem getSpecialItemByDisplayName(String display_name) {
		for (SpecialItems i : SpecialItems.values()) {
			if (i.getItem().getDisplayName().equalsIgnoreCase(display_name)) {
				return i.getItem();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the Enum representation of the Special Item by its display name. Returns null if none was found.
	 * @param display_name the display name of the SpecialItem
	 * @return the Enum representation or null if not found.
	 */
	public static SpecialItems getSpecialItemEnumByDisplayName(String display_name) {
		for (SpecialItems i : SpecialItems.values()) {
			if (i.getItem().getDisplayName().equalsIgnoreCase(display_name)) {
				return i;
			}
		}
		
		return null;
	}
	
	public SpecialItem getItem() {
		return SPECIAL_ITEM;
	}
}
