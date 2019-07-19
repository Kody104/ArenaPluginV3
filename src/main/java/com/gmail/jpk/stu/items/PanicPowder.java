package com.gmail.jpk.stu.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;

public class PanicPowder extends SpecialItem implements UsableItem {

	public PanicPowder() {
		super(Material.BLAZE_POWDER, SpecialItems.PANIC_POWDER.getUID(), SpecialItems.PANIC_POWDER.getDisplayName(), "Causes enemies to attack each other when left behind.");
	}

	@Override
	public void useItem(Player player) {
		player.sendMessage("Using item!");
		player.getInventory().remove(this);
	}
}
