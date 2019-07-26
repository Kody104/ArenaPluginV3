package com.gmail.jpk.stu.tasks;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.arena.GlobalW;

public class DelayedMessageTask extends BukkitRunnable {

	private String message;
	private Player player;
	
	public DelayedMessageTask(Player player, String message) {
		setPlayer(player);
		setMessage(message);
	}
	
	@Override
	public void run() {
		GlobalW.toPlayer(player, message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
	
}
