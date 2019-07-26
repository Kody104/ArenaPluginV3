package com.gmail.jpk.stu.items;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EmergencyTeleport extends SpecialItem implements UsableItem {

	public EmergencyTeleport() {
		super(Material.ENDER_PEARL, 900, SpecialItems.EMERGENCY_PORT.getUID(), SpecialItems.EMERGENCY_PORT.getDisplayName(), "Gets you out of a tight spot.", "Causes temporary blindness.");

	}
	
	@Override
	public void useItem(Player player) {
		int duration = 60; //in ticks
		
		player.sendMessage("I will be fully implemented when spawn locations are implemented.");
		player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, duration, 1));
	}

}
