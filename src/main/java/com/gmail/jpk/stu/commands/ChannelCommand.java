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

public abstract class ChannelCommand extends BasicCommand {

	//The list of players locked into their channels
	private Set<UUID> locked_players;
	
	public ChannelCommand(ArenaPlugin plugin) {
		super(plugin);
		
		this.setLocked_players(new HashSet<UUID>());
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
		
		Player player = (Player) sender;
		UUID uid = player.getUniqueId();
		ChatSystem system = GlobalW.getChatSystem();
		Role role = system.getRole(uid);
		
		if (role == null) {
			system.messageDevChannel(null, String.format("Warning: %s was not registered in the ChatSystem. Adding them as a Player.", player.getName()));
			system.addPlayer(uid, Role.PLAYER);
			role = Role.PLAYER;
		}
		
		//Handles /ch -lock
		if (args[0].equalsIgnoreCase("-lock")) {
			if (locked_players.contains(uid)) {
				locked_players.remove(uid);
				ChatSystem.toPlayer(player, ChatColor.YELLOW + "You are now speaking publicly.");
				return true;
			} 
			
			//No need for players to lock their channel.
			if (role == Role.PLAYER) {
				ChatSystem.toPlayer(player, "Players are unable to lock their channel.");
				return true;
			}
			
			locked_players.add(uid);
			ChatSystem.toPlayer(player, ChatColor.GREEN + "You are now speaking privately to your default channel.");
			return true;
		}
		
		//Create the message this person has sent.
		String message = getMessage(args);
		
		//Is this player speaking publicly?
		if (!locked_players.contains(uid)) {
			system.messageAll(uid, message);
		} 
		
		else {
			//VIP
			if (system.isVip(uid)) {
				system.messageVIPChannel(uid, message);
				return true;
			}
			//DEV
			else if (system.isDev(uid)) {
				system.messageDevChannel(uid, message);
			}
			//Default
			else {
				system.messageAll(uid, message);
			}
		}
		
		return true;
	}
	
	/**
	 * Creates a message from a string of input
	 * @param args the arguments forming the message
	 * @return the trimmed message
	 */
	protected String getMessage(String[] args) {
		String message = "";
		
		for (int i = 0; i < args.length; i++) {
			message = args[i] + " ";
		}
		
		return message.trim();
	}

	public Set<UUID> getLocked_players() {
		return locked_players;
	}

	public void setLocked_players(Set<UUID> locked_players) {
		this.locked_players = locked_players;
	}
}
