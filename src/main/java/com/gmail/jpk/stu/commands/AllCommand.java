package com.gmail.jpk.stu.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.arena.ChatSystem.Role;

public class AllCommand extends ChannelCommand {

	public AllCommand(ArenaPlugin plugin) {
		super(plugin, Role.ALL);
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Verify there is a message
		if (args.length == 0) {
			return false;
		}
		
		String message = getMessage(args);
		
		//If console, then send as announcement
		if (sender instanceof ConsoleCommandSender) {
			GlobalW.getChatSystem().messageAllPlayers("Console", message);
			return true;
		}
		
		//If player, then send through all-channel
		else if (sender instanceof Player) {
			GlobalW.getChatSystem().messageAllPlayers(((Player) sender).getName(), message);
			return true;
		}
		
		return false;
	}
}
