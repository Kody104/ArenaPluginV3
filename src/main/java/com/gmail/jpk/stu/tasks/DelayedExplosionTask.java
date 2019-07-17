package com.gmail.jpk.stu.tasks;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class DelayedExplosionTask extends BukkitRunnable {

	private Player player;
	
	public DelayedExplosionTask(Player player) {
		setPlayer(player);
	}
	
	@Override
	public void run() {
		World world = player.getWorld();
		world.createExplosion(player.getLocation(), 2F);
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
