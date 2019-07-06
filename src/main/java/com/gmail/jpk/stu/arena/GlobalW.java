package com.gmail.jpk.stu.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;

import com.gmail.jpk.stu.Entities.ArenaPlayer;

public class GlobalW {
	
	private static ArenaPlugin plugin;
	private static int round = 0;
	private static boolean hasStarted = false;
	private static World inWorld;
	private List<ArenaPlayer> playersInArena = new ArrayList<ArenaPlayer>();
	
	public static ArenaPlugin getPlugin() {
		return plugin;
	}
	public static int getRound() {
		return round;
	}
	public static boolean isHasStarted() {
		return hasStarted;
	}
	public static World getInWorld() {
		return inWorld;
	}
	public List<ArenaPlayer> getPlayersInArena() {
		return playersInArena;
	}
}
