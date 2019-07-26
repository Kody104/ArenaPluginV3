package com.gmail.jpk.stu.tasks;

import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.commands.ReadyCommand;

public class StartArenaTask extends BukkitRunnable {

	@Override
	public void run() {
		GlobalW.nextRound();
		ReadyCommand.setAllReady(false);
		ReadyCommand.unreadyAllPlayers();
	}

}
