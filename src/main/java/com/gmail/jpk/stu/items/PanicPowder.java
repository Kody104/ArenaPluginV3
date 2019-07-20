package com.gmail.jpk.stu.items;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class PanicPowder extends SpecialItem implements UsableItem {

	public PanicPowder() {
		super(Material.BLAZE_POWDER, SpecialItems.PANIC_POWDER.getUID(), SpecialItems.PANIC_POWDER.getDisplayName(), "Causes enemies to attack each other when left behind.");
	}

	@Override
	public void useItem(Player player) {
		List<Entity> entities = player.getNearbyEntities(15, 5, 15);
		List<Creature> creatures = new ArrayList<Creature>();
		
		player.playSound(player.getLocation(), Sound.BLOCK_BELL_USE, 1f, 1f);
		
		for (Entity e : entities) {
			if (e instanceof Creature) {
				creatures.add( (Creature) e);
			}
		}
		
		for (Creature creature : creatures) {
			creature.setTarget( creatures.get((int)(Math.random() * creatures.size())) );
		}
	}
}
