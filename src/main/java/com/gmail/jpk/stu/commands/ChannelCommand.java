package com.gmail.jpk.stu.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem;
import com.gmail.jpk.stu.arena.ChatSystem.Role;
import com.gmail.jpk.stu.arena.GlobalW;

public class ChannelCommand extends BasicCommand {

	//The list of players locked into their channels
	private static Set<UUID> locked_players = new HashSet<UUID>();
	
	public ChannelCommand(ArenaPlugin plugin) {
		super(plugin);		
	}
	
	/**
	 * Gets the of UUIDs for all of the Online Players.
	 * @return
	 */
	protected Set<UUID> getOnlinePlayers() {
		Set<UUID> set = new HashSet<UUID>();
		
		//Grab the UUIDs
		for (Player player : Bukkit.getOnlinePlayers()) {
			set.add(player.getUniqueId());
		}
		
		return set;
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//This command needs at least 1 arg
		if (args.length < 1) {
			return false;
		}
		
		//Console shouldn't use this command.
		if (sender instanceof ConsoleCommandSender) {
			sender.sendMessage("The console may not use this command.");
			return true;
		}

		//Grab Player and UUID
		Player player = (Player) sender;
		UUID uid = player.getUniqueId();
		
		//Grab ChatSystem and Player ChatRole
		ChatSystem system = GlobalW.getChatSystem();
		Role role = system.getRole(uid);
		
		//Grab what they said
		String message = getMessage(args);
		
		//Verify the player has a role
		if (role == null) {
			system.toPlayer(player, "You aren't registered in the ChatSystem. You will be added as a player.");
			system.addPlayer(uid, Role.PLAYER);
			system.messageAll(uid, message);
			return true;
		}
		
		//Player can't use this command (no need to)
		if (role == Role.PLAYER) {
			system.toPlayer(player, "Players can't lock their channel.");
			return true;
		}
		
		//Does the player want to lock themselves into their channel?
		if (args[0].equalsIgnoreCase("-lock")) {
			//Are they already locked?
			if (locked_players.contains(uid)) {
				locked_players.remove(uid);
				system.toPlayer(player, String.format("You are now speaking %s!", ChatColor.GOLD + "publicly" + ChatColor.RESET));
				return true;
			}
 			
			//Add them to the locked list
			locked_players.add(uid);
			system.toPlayer(player, String.format("You are now speaking %s to your channel.", ChatColor.GOLD + "privately" + ChatColor.RESET));
			return true;
		}
		
			//Message the appropriate channel
			switch (role) {
				case DEV:
				{
					system.messageDevs(uid, message);
					return true;
				}
				
				case VIP:
				{
					system.messageVips(uid, message);
					return true;
				}
				
				default:
				{
					system.messageDevs(null, String.format("Unknown condition. %s (%s) is but unable to message their channel.", player.getName(), "" + role));
					system.messageAll(uid, message);
					return true;
				}
			}
	}
	
	/**
	 * Creates a message from a string of input
	 * @param args the arguments forming the message
	 * @return the trimmed message
	 */
	protected String getMessage(String[] args) {
		String message = "";
		
		for (String string : args) {
			message += (string + " ");
		}
		
		return message.trim();
	}

	public static Set<UUID> getLockedPlayers() {
		return locked_players;
	}

	public static void setLockedPlayers(Set<UUID> locked_players) {
		ChannelCommand.locked_players = locked_players;
	}
}
