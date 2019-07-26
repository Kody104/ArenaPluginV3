package com.gmail.jpk.stu.commands;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem;
import com.gmail.jpk.stu.arena.ChatSystem.Role;
import com.gmail.jpk.stu.arena.GlobalW;

public abstract class ChannelCommand extends BasicCommand {

	protected ChatSystem.Role role;
	
	public ChannelCommand(ArenaPlugin plugin, Role role) {
		super(plugin);
		
		setRole(role);
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
		//Verify length
		if (args.length == 0) {
			return false;
		}
		
		//Get Message
		String message = this.getMessage(args);
		
		//Handle console
		if (sender instanceof ConsoleCommandSender) {
			GlobalW.getChatSystem().messagePlayersByRole(null, role, message);
			return true;
		}
		
		else if (sender instanceof Player) {
			GlobalW.getChatSystem().messagePlayersByRole(((Player) sender).getUniqueId(), role, message);
			return true;
		}
		
		
		return false;
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
	
	public ChatSystem.Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
}
