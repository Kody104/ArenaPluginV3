package com.gmail.jpk.stu.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.arena.GlobalW.ErrorMsgs;

public abstract class BasicCommand implements CommandExecutor {

	protected ArenaPlugin plugin;
	
	public BasicCommand(ArenaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public abstract boolean performCommand(CommandSender sender, String args[]);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		return performCommand(sender, args);
	}
	
	/**
	 * Manually executes a command.
	 * @param commandLine the command and its args.
	 */
	public static void executeCommand(String commandLine) {
		Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), commandLine);
	}
	
	/**
	 * <b>Returns a valid CommandSender back as an ArenaPlayer.</b><br/>
	 * If the CommandSender isn't an ArenaPlayer, then it returns null.
	 * This function will alert the sender if they are not an ArenaPlayer.
	 * @param sender the CommandSender
	 * @return The CommandSender as an ArenaPlayer. 
	 */
	protected ArenaPlayer getArenaPlayer(CommandSender sender) {
		//Validate the CommandSender is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage(ErrorMsgs.NOT_PLAYER.getMessage());
			return null;
		}
		
		Player player = (Player) sender;
		ArenaPlayer arena_player = GlobalW.getArenaPlayer(player);
		
		if (arena_player == null) {
			GlobalW.toPlayerError(player, ErrorMsgs.NOT_ARENA_PLAYER.getMessage());
		}
		
		return arena_player;
	}
	
	/**
	 * Gets a player by their name. Returns null if they do not exist or are offline.
	 * @param name the name of the player
	 * @return the Player or null if not found / offline.
	 */
	protected Player getPlayer(String name) {
		for (Player player : Bukkit.getOnlinePlayers()) {
			if (player.getName().equalsIgnoreCase(name)) {
				return player;
			}
		}
		
		return null;
	}
	
	/**
	 * Checks if the given string input is a valid integer.
	 * @param string the input to validate
	 * @return the valid int or -1 if invalid
	 */
	protected int getValidInteger(String string) {
		int i = 0;
		
		try {
			i = Integer.parseInt(string);
			return i;
		} catch (NumberFormatException e) {
			return -1;
		}
	}
	
	/**
	 * Checks if a String has a proper Boolean value.
	 * Note: Does NOT return the respective boolean value.
	 * @param value the value to check
	 * @return true if the value is "true" or "false"
	 */
	protected boolean isBoolean(String value) {
		return (value.equalsIgnoreCase("true") || value.equalsIgnoreCase("false"));
	}
	
	public ArenaPlugin getPlugin() {
		return plugin;
	}

}
