package com.gmail.jpk.stu.arena;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.tasks.DelaySpawnTask;

public class GlobalW {
	
	private static ArenaPlugin plugin;
	private static final int maxSize = 6;
	private static int round = 0;
	private static boolean hasStarted = false;
	private static World inWorld;
	private static List<ArenaPlayer> playersInArena = new ArrayList<ArenaPlayer>();
	private static List<ArenaCreature> creaturesInArena = new ArrayList<ArenaCreature>();
	private static List<Location> playerSpawnLocations = new ArrayList<Location>();
	
	//TODO: This needs to init all the important things
	public static void initialize(ArenaPlugin plugin) {
		setPlugin(plugin);
		inWorld = plugin.getServer().getWorlds().get(0); // This is the overworld
		playerSpawnLocations.add(new Location(inWorld, -844.245d, 115.0d, -1296.964d)); // This is the forest arena. Index 0
	}
	
	/**
	 * Sends a message to the Player with the ChatTag
	 * @param player the player to whom will receive the message
	 * @param message what the message should be
	 */
	public static void toPlayer(Player player, String message) {
		player.sendMessage(String.format("%s %s", getChatTag(), message));
	}
	
	/**
	 * Sends a message to the Player with the ChatTag
	 * @param player the player to whom will receive the message
	 * @param message what the message should be
	 */
	public static void toPlayerError(Player player, String message) {
		player.sendMessage(String.format("%s %s", getChatErrorTag(), message));
	}
	
	/**
	 * This will send a message to all the arena players.
	 * @param message	The message you want sent.
	 */
	public static void toArenaPlayers(String message) {
		for(ArenaPlayer player : playersInArena) {
			player.getmPlayer().sendMessage(message);
		}
	}
	
	/**
	 * This will teleport all the arena players to a desired location.
	 * @param loc	The location to teleport to.
	 */
	public static void teleArenaPlayers(Location loc) {
		for(int i = 0; i < playersInArena.size(); i++) {
			playersInArena.get(i).getmPlayer().teleport(loc);
		}
	}
	
	/**
	 * This will run automatically at the end of rounds until the round number divisible by 5. Then we send them to the shop.
	 */
	public static void nextRound() {
		round++;
		toArenaPlayers("Round " + round + " has started!");
		switch(round)
		{
			case 1:
			{
				/* We need to create the arenas before we can set an actual location for these spawns. */
				new DelaySpawnTask(new Location(inWorld, 0.0d, 0.0d, 0.0d), EntityType.ZOMBIE, 1).runTaskLater(plugin, 0); // The last arg for this is 0. v
				/*new DelaySpawnTask(etc, etc, etc).runTaskLater(plugin, 0+20) */ // We do plus 20 per entity that we spawn in the arena.
				break;
			}
			default:
			{
				break;
			}
		}
	}
	
	public static void setPlugin(ArenaPlugin aplug) {
		plugin = aplug;
	}
	
	public static String getChatTag() {
		return ("<Arena-Plugin> ");
	}
	
	public static String getChatErrorTag() {
		return ("<Arena-Error> " + ChatColor.RED);
	}
	
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
	
	public static List<ArenaPlayer> getPlayersInArena() {
		return playersInArena;
	}
	
	public static List<ArenaCreature> getCreaturesInArena() {
		return creaturesInArena;
	}


	public static enum ErrorMsgs { 
		NOT_PLAYER("You must be a player!"), 
		NOT_ARENA_PLAYER("You are not an arena player!");
		
		private String message;
		
		ErrorMsgs(String message) {
			this.message = message;
		}
		
		public String getMessage() {
			return message;
		}
	}
}
