package com.gmail.jpk.stu.items;

import org.bukkit.ChatColor;
import org.bukkit.Material;

/**
 * The TestDummyStick (UID: 100) is a SpecialItem that can have any ability or effect needed.<br/><br/>
 * It is intended to use for debugging abilities or testing out new features.
 * It is NOT intended to be a general use SpecialItem.
 */
public class TestDummyStick extends SpecialItem {

	public TestDummyStick() {
		super(Material.STICK, 100, ChatColor.YELLOW + "Test Dummy Stick", new String[] { "This item is intended to be used as a debugging tool." });
	}

}
