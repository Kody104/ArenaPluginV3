package com.gmail.jpk.stu.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.World;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;

public class GlobalW {
	
	private static ArenaPlugin plugin;
	private static final int maxSize = 6;
	private static int round = 0;
	private static boolean hasStarted = false;
	private static World inWorld;
	private static List<ArenaPlayer> playersInArena = new ArrayList<ArenaPlayer>();
	
	public static ArenaPlugin getPlugin() {
		return plugin;
	}
	public static int getMaxSize() {
		return maxSize;
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
	public static ArenaPlayer getArenaPlayer(Player p) {
		for(ArenaPlayer player : playersInArena) {
			if(player.getmPlayer().getUniqueId() == p.getUniqueId()) {
				return player;
			}
		}
		return null;
	}
	public static void removeArenaPlayer(Player p) {
		for(int i = 0; i < playersInArena.size(); i++) {
			ArenaPlayer player = playersInArena.get(i);
			if(player.getmPlayer().getUniqueId() == p.getUniqueId()) {
				playersInArena.remove(i);
				i--;
			}
		}
	}
	public static void toArenaPlayers(String message) {
		for(ArenaPlayer player : playersInArena) {
			player.getmPlayer().sendMessage(message);
		}
	}
	public static List<ArenaPlayer> getPlayersInArena() {
		return playersInArena;
	}
	
	public static enum ErrorMsgs { 
		NOT_PLAYER("[Error] You must be a player!"), 
		NOT_ARENA_PLAYER("[Error] You are not an arena player!");
		
		private String message;
		
		ErrorMsgs(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
	}
}
