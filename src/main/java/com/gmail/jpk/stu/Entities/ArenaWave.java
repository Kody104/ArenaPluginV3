package com.gmail.jpk.stu.Entities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.tasks.DelaySpawnTask;

/**
 * Creatures a wave and schedules the spawns for the enemies. This class is meant to make scheduling spawns easier.
 * It does NOT keep track of the enemies. See GlobalW.creaturesInArena for a list.
 *
 */
public class ArenaWave {
	
	//The list of enemies to spawn
	private List<EntityType> enemies;
	
	/**
	 * Creates an ArenaWave with a given list. Use startWaveSequentially() or startGroupedWave() to spawn the enemies.
	 * @param enemies the list of enemies to spawn
	 */
	public ArenaWave(List<EntityType> enemies) {
		setEnemies(enemies);
	}
	
	/**
	 * Creates an ArenaWave with no list.
	 */
	public ArenaWave() {
		this.enemies = new ArrayList<EntityType>();
	}
	
	/**
	 * Creates an ArenaWave with a basic list which has the following properties:
	 * @param total_enemies the TOTAL number of enemies to spawn in this wave
	 * @param min the MINIMUM number of each enemy type to spawn
	 * @param types the types of enemies the function can choose from
	 * @return An ArenaWave object ready to start the wave if desired.
	 */
	public static ArenaWave createBasicWave(int total_enemies, int min, EntityType... types) {
		ArenaWave wave = new ArenaWave();
		
		//First add in the minimum number for each type
		for (EntityType type : types) {
			for (int i = 0; i < min; i++) {
				wave.add(type);
			}
		}
		
		//Then add in random enemies to fill in the blanks.
		int enemies_left = (total_enemies - wave.enemies.size());
		int length = types.length;
		int index = 0;
		
		for (int i = 0; i < enemies_left; i++) {
			index = GlobalW.rand.nextInt(length);
			wave.add(types[index]);
		}
		
		
		return wave;
	}
	
	/**
	 * Creates an ArenaWave from a HashMap where the key is the type of enemy and the value is the quantity.
	 * @param data the data for the round
	 */
	public static ArenaWave createWaveFromHashMap(HashMap<EntityType, Integer> data) {
		ArenaWave wave = new ArenaWave();
		Set<EntityType> keys = data.keySet();
		
		//Iterate through the map
		for (EntityType key : keys) {
			for (int i = 0; i < data.get(key); i++) {
				wave.add(key);
			}
		}
		
		return wave;
	}
	
	/**
	 * Add an enemy to the wave
	 * @param type the type of wave
	 */
	public void add(EntityType type) {
		this.enemies.add(type);
	}
	
	public Location getRandomSpawnLocation() {
//		List<Location> locations = GlobalW.getPlayerSpawnLocations();
	
		return new Location(GlobalW.getInWorld(), 0, 0, 0);
//		return locations.get(GlobalW.rand.nextInt(locations.size()));
	}
	
	/**
	 * Spawns enemies in the wave sequentially (i.e. in the order they appear in the list).
	 * @param level the level of the enemies
	 * @param delay the delay (in ticks) between enemies
	 */
	public void startWaveSequentially(int level, int delay) {
		Plugin plugin = GlobalW.getPlugin();
		
		//Schedule the spawns sequentially
		for (int i = 0; i < enemies.size(); i++) {
			new DelaySpawnTask(getRandomSpawnLocation(), enemies.get(i), level).runTaskLater(plugin, (delay * i)); 
		}
	}
	
	/**
	 * Spawns enemies in the wave in groups separated by a delay.
	 * The number of enemies to spawn MUST be divisible by the delay between groups.
	 * @param level the level of the enemies
	 * @param delay the delay between individual enemy spawns
	 * @param delay_between_groups the delay between the groups
	 */
	public void startGroupedWave(int level, int group_size, int delay, int delay_between_groups) {
		int size = enemies.size();
		
		//Verify there are even groups
		if (size % group_size != 0) {
			return;
		}
		
		//Grab references
		World world = GlobalW.getInWorld();
		Plugin plugin = GlobalW.getPlugin();
		
		//Adjusted Delay
		int adj_delay = 0;
		
		//Schedule the spawns in groups
		for (int i = 0; i < size; i++) {
			for (int k = 0; k < group_size; k++) {
				adj_delay = (delay_between_groups * k) + (delay * i);
				new DelaySpawnTask(new Location(world, 0.0d, 0.0d, 0.0d), enemies.get(i), level).runTaskLater(plugin, adj_delay); 
			}
		}
	}

	public List<EntityType> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<EntityType> enemies) {
		this.enemies = enemies;
	}
}
