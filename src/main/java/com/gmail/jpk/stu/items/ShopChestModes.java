package com.gmail.jpk.stu.items;

/**
 * Represents various modes the ShopChest can have. Each mode has a unique array of items. 
 */
public enum ShopChestModes {
	
	BASIC( new SpecialItem[] { SpecialItems.getSpecialItemByUID(102), SpecialItems.getSpecialItemByUID(103), SpecialItems.getSpecialItemByUID(104), SpecialItems.getSpecialItemByUID(105) } ),
		;
		
	private final SpecialItem[] ITEMS;
		
	private ShopChestModes(final SpecialItem[] ITEMS) {			
		this.ITEMS = ITEMS;
	}
		
	public SpecialItem[] getItems() {
		return ITEMS;		
	}
	
}
