package com.gmail.jpk.stu.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.tasks.DelayedExplosionTask;

public enum SpecialItems {
	
	//NOTE: Enums values are organized by UID
	TEST_DUMMY_STICK	(new SpecialItem(Material.STICK, 100, ChatColor.YELLOW + "Test Dummy Stick", new String[] { "This item is intended to be used as a debugging tool." }) ),
	GOLDEN_SCRAP		(new SpecialItem(Material.GOLD_NUGGET, 101, ChatColor.GOLD + "Golden Scrap", new String[] { "Don't let its color fool you - it's not even worth its weight." }) ),
	BLAZING_BOOM_ROD	(new SpecialItem(Material.BLAZE_ROD, 102, ChatColor.RED + "Blazing Boom Rod", new String[] { "Explodes when left behind." })),
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
			
			case BLAZING_BOOM_ROD:
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
		
		switch (uid) {
			case 100:
			{
				return TEST_DUMMY_STICK.getItem();
			}
			case 101:
			{
				return GOLDEN_SCRAP.getItem();
			}
			default:
			{
				return null;
			}
		}
	}
	
	public SpecialItem getItem() {
		return SPECIAL_ITEM;
	}
}
