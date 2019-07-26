package com.gmail.jpk.stu.items;

import org.bukkit.Material;

public enum SpecialItems {
	
	/*
	 * References all the SpecialItem classes. This enum allows us to quickly find SpecialItems by UID or display name.
	 * NOTE: When creating a UsableItem ensure that the constructor references SpecialItems.VALUE.getUID() and SpecialItems.VALUE.getDisplayName() for consistency.
	 * 
	 * UID Codes:
	 * 100 through 170 are for common SpecialItems that can be purchased or found in the arena as drops.
	 * 171 through 180 are for SpecialItems that have a unique purpose (i.e. test dummy stick, shop chests, etc.)
	 * 180 through 199 are for SpecialItems that are dropped by BOSSES.
	 * 
	 */
	GOLDEN_SCRAP     (101, "Golden Scrap"),
	GOLDEN_BAR       (102, "Golden Bar"),
	CHIPPED_EMERALD  (103, "Chipped Emerald"),
	FLAWLESS_DIAMOND (104, "Flawless Diamond"),
	BOOM_STICK       (105, "Boom Stick"),
	PANIC_POWDER     (106, "Panic Powder"),
	DECOY_BOY_TROY   (107, "Summon Decoy Boy Troy"),
	EMERGENCY_PORT   (108, "Emergency Teleport"),
	TASTY_STEAK      (109, "Tasty Steak"),
	TEST_DUMMY_STICK (180, "Test Dummy Stick"),
	SHOP_CHEST       (181, "Shop Chest"),
	;

	private final String DISPLAY_NAME;
	private final int UID;
	
	private SpecialItems(final int uid, final String display_name) {
		this.DISPLAY_NAME = display_name;
		this.UID = uid;
	}
	
	/**
	 * Gets the SpecialItem this value represents. Returns null if not found.
	 * @return the SpecialItem
	 */
	public SpecialItem getSpecialItem() {
		switch (this) {
			//Unusable Items
			case GOLDEN_SCRAP:
				return new SpecialItem(Material.GOLD_NUGGET, 1, GOLDEN_SCRAP.UID, GOLDEN_SCRAP.DISPLAY_NAME, "It's barely worth its weight.");
			case GOLDEN_BAR:
				return new SpecialItem(Material.GOLD_INGOT, 9, GOLDEN_BAR.UID, GOLDEN_BAR.DISPLAY_NAME, "Compactly store your scraps.");
			case CHIPPED_EMERALD:
				return new SpecialItem(Material.EMERALD, 900, CHIPPED_EMERALD.UID, CHIPPED_EMERALD.DISPLAY_NAME, "Worth a modest value.");
			case FLAWLESS_DIAMOND:
				return new SpecialItem(Material.DIAMOND, 90000, FLAWLESS_DIAMOND.UID, FLAWLESS_DIAMOND.DISPLAY_NAME, "Use it to flaunt your wealth around.");
			
			//Usable Items with their own class.
			case TEST_DUMMY_STICK:
				return new TestDummyStick();
			case PANIC_POWDER:
				return new PanicPowder();
			case BOOM_STICK:
				return new BoomStick();
			case SHOP_CHEST:
				return new ShopChest();
			case DECOY_BOY_TROY:
				return new DecoyBoyTroy();
			case EMERGENCY_PORT:
				return new EmergencyTeleport();
			case TASTY_STEAK:
				return new TastySteak();
			default:
				return null;
		}
	}
	
	/**
	 * Gets a SpecialItem by its UID. Returns null if not found.
	 * @param uid the unique id of the item
	 * @return the SpecialItem or null if not found.
	 */
	public static SpecialItem getSpecialItemByUID(int uid) {
		for (SpecialItems i : SpecialItems.values()) {
			if (i.UID == uid) {
				return i.getSpecialItem();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets a SpecialItem by its display name. Returns null if not found.
	 * @param String display_name the display name of the SpecialItem
	 * @return the SpecialItem or null if not found
	 */
	public static SpecialItem getSpecialItemByDisplayName(String display_name) {
		for (SpecialItems i : SpecialItems.values()) {
			if (i.DISPLAY_NAME.equalsIgnoreCase(display_name)) {
				return i.getSpecialItem();
			}
		}
		
		return null;
	}
	
	/**
	 * Gets the display name for the Special Item.
	 * @return the display name
	 */
	public String getDisplayName() {
		return DISPLAY_NAME;
	}
	
	/**
	 * Gets the UID for the Special Item
	 * @return the UID
	 */
	public int getUID() {
		return UID;
	}
 }
