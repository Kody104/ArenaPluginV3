package com.gmail.jpk.stu.items;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.jpk.stu.arena.GlobalW;

public class DecoyBoyTroy extends SpecialItem implements UsableItem {
	
	public DecoyBoyTroy() {
		super(Material.LEATHER, 900, SpecialItems.DECOY_BOY_TROY.getUID(), SpecialItems.DECOY_BOY_TROY.getDisplayName(), "Summons your boy Troy.");
	}

	@Override
	public void useItem(Player player) {
		Location location = player.getLocation();
		
		LivingEntity cow = (LivingEntity) GlobalW.getInWorld().spawnEntity(location, EntityType.COW);
		cow.setCustomName("Decoy Boy Troy");
		player.sendMessage("<Decoy Boy Troy>" + ChatColor.GOLD + " YEE HAW! Let's kill some monsters!");
		int radius = 15;
		
		cow.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 600, 3));
		cow.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 600, 1));
			
		for (Entity e : cow.getNearbyEntities(radius, 5, radius)) {
			if (e instanceof Creature) {
				((Creature) e).setTarget(cow);		
			}
		}
	}
}