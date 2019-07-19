package com.gmail.jpk.stu.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class BoomStick extends SpecialItem implements UsableItem {

	public BoomStick() {
		super(Material.STICK, SpecialItems.BOOM_STICK.getUID(), SpecialItems.BOOM_STICK.getDisplayName(), "Explodes when left behind.");
	}

	@Override
	public void useItem(Player player) {
		player.sendMessage("I will explode eventually.");
	}
	
}
