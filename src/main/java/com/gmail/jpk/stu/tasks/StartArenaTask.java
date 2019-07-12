package com.gmail.jpk.stu.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.arena.GlobalW;

public class StartArenaTask extends BukkitRunnable {

	@Override
	public void run() {
		GlobalW.nextRound();
	}

}
