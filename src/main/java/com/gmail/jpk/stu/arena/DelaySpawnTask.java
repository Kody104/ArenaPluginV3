package com.gmail.jpk.stu.arena;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.abilities.PassiveAbility;

public class DelaySpawnTask extends BukkitRunnable {

	private Location spawnPoint; // The point you want to spawn this creature in at
	private EntityType entityType; // The creature type you want to spawn
	private int lvl; // The level of the creature to spawn
	
	public DelaySpawnTask(Location spawnPoint, EntityType entityType, int lvl) {
		this.spawnPoint = spawnPoint;
		this.entityType = entityType;
		this.lvl = lvl;
	}
	
	@Override
	public void run() {
		LivingEntity le = (LivingEntity) GlobalW.getInWorld().spawnEntity(spawnPoint, entityType); // Spawns the livingentity in the world.
		PassiveAbility a = null;
		switch(entityType) {
			case SPIDER:
			{
				a = PassiveAbility.POISON_BITE; // Gives spider his passive
				break;
			}
			default:
			{
				break;
			}
		}
		GlobalW.getCreaturesInArena().add(new ArenaCreature(le, lvl, a)); // Adds the creature to our list to keep track of it.
	}

}
