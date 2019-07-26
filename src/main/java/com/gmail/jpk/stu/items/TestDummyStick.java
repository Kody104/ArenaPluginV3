package com.gmail.jpk.stu.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

/**
 * The TestDummyStick (UID: 100) is a SpecialItem that can have any ability or effect needed.<br/><br/>
 * It is intended to use for debugging abilities or testing out new features.
 * It is NOT intended to be a general use SpecialItem.
 */
public class TestDummyStick extends SpecialItem implements UsableItem {

	public TestDummyStick() {
		super(Material.STICK, 900, SpecialItems.TEST_DUMMY_STICK.getUID(), SpecialItems.TEST_DUMMY_STICK.getDisplayName(), new String[] { "Intended to be used as a debugging tool." });
	}
	
	@Override
	public void useItem(Player player) {
		player.sendMessage("I can do whatever you'd like...just gotta program it in first.");
	}

}
