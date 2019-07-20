package com.gmail.jpk.stu.tasks;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedExplosionTask extends BukkitRunnable {

	private Player player;
	private Location location;
	
	public DelayedExplosionTask(Player player) {
		setPlayer(player);
		setLocation(player.getLocation());
	}
	
	@Override
	public void run() {
		World world = player.getWorld();
		world.createExplosion(location, 2F);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}
	
}
