package com.gmail.jpk.stu.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem;
import com.gmail.jpk.stu.arena.ChatSystem.Role;
import com.gmail.jpk.stu.arena.GlobalW;

public class ChatSystemCommand extends BasicCommand {

	public ChatSystemCommand(ArenaPlugin plugin) {
		super(plugin);
	}

			//chsys set PLAYER ROLE
	
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Verify length
		if (args.length < 2) {
			return false;
		}
		
		ChatSystem system = GlobalW.getChatSystem();
		
		//Commands with two arguments
		if (args.length == 2) {			
			//Validate player arguments
			if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("role")) {
				Player player = this.getPlayer(args[1]);
				
				//Check player is online
				if (player == null) {
					sender.sendMessage(GlobalW.getChatErrorTag() + String.format("%s was not found.", args[1]));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("role")) {
					Role role = GlobalW.getChatSystem().getRole(player.getUniqueId());
					
					if (role != null) {
						sender.sendMessage(ChatSystem.getDefaultChatTag() + String.format("%s is a " + role, player.getName()));
						return true;
					}
					
					sender.sendMessage(ChatSystem.getDefaultChatTag() + String.format("%s has no role.", player.getName()));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("remove") && GlobalW.getChatSystem().removePlayer(player.getUniqueId())) {
					sender.sendMessage(GlobalW.getChatTag() + String.format("%s was successfully removed from the ChatSystem.", player.getName()));
					return true;
				}
				
				sender.sendMessage(GlobalW.getChatTag() + String.format("Unable to find %s from the ChatSystem.", player.getName()));
				return true;
			}
			
			//Verify debug, color
			if (args[0].equalsIgnoreCase("debug") || args[0].equalsIgnoreCase("color")) {
				if (!isBoolean(args[1])) {
					sender.sendMessage(GlobalW.getChatErrorTag() + String.format("%s isn't a valid input.", args[1]));
					return true;
				}
				
				if (args[0].equalsIgnoreCase("debug")) {
					system.setShowDebugOutput(Boolean.valueOf(args[1].toLowerCase()));
					sender.sendMessage(ChatSystem.getDefaultChatTag() + String.format("Set show-debug-text to %s.", args[1]).toUpperCase());
					return true;
				}
				
				//Otherwise it's color
				system.setColorCodesEnabled(Boolean.valueOf(args[1].toLowerCase()));
				sender.sendMessage(ChatSystem.getDefaultChatTag() + String.format("Set show-debug-text to %s.", args[1]).toUpperCase());
				return true;
			}
		}
		
		//more commands to do later -- gotta catch the bus lmao
		
		return false;
	}

}
