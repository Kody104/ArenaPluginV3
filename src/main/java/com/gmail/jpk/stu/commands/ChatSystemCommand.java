package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem;
import com.gmail.jpk.stu.arena.ChatSystem.Role;
import com.gmail.jpk.stu.arena.GlobalW;

/**
 * Runs tasks for the ChatSystem.<br/><br/>
 * 
 * Current Commands
 * <ul>
 * 	<li>chsys role PLAYER -> gets the chat-role of the player.</li>
 * 	<li>chsys remove PLAYER -> removes the name of the player.</li>
 * 	<li>chsys debug true|false -> toggles debug text for the dev channel.</li>
 * 	<li>chsys color true|false -> toggles if color codes are allowed in-line for VIPs and DEVs.</li>
 *  <li>chsys set PLAYER ROLE -> sets the player to a role.</li>
 * </ul> 
 *
 */
public class ChatSystemCommand extends BasicCommand {

	public ChatSystemCommand(ArenaPlugin plugin) {
		super(plugin);
	}	
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Verify length
		if (args.length < 2) {
			return false;
		}
		
		ChatSystem system = GlobalW.getChatSystem();
		
		//Commands with two arguments
		if (args.length == 2) {			
			//Check commands remove and role (grouped since they share info)
			if (args[0].equalsIgnoreCase("remove") || args[0].equalsIgnoreCase("role")) {
				Player player = this.getPlayer(args[1]);
				
				//Check player is online
				if (player == null) {
					sender.sendMessage(GlobalW.getChatErrorTag() + String.format("%s was not found.", args[1]));
					return true;
				}
				
				//Run command /chsys role PLAYER
				if (args[0].equalsIgnoreCase("role")) {
					Role role = system.getRole(player.getUniqueId());
					
					if (role != null) {
						system.messageCommandSender(sender, String.format("%s is a %s.", role.toString(), player.getName()));
						return true;
					}
					
					system.messageCommandSender(sender, String.format("%s has no role.", player.getName()));
					return true;
				}
				
				//Run command /chysys remove PLAYER
				if (args[0].equalsIgnoreCase("remove") && GlobalW.getChatSystem().removePlayer(player.getUniqueId())) {
					system.messageCommandSender(sender, String.format("%s was successfully removed from the ChatSystem.", player.getName()));
					return true;
				}
				
				system.messageCommandSender(sender, ChatColor.RED + String.format("Unable to find %s from the ChatSystem.", player.getName()));
				return true;
			}
			
			//Verify debug, color (Grouped since they share info)
			if (args[0].equalsIgnoreCase("debug") || args[0].equalsIgnoreCase("color")) {
				if (!isBoolean(args[1])) {
					system.messageCommandSender(sender, ChatColor.RED + String.format("%s isn't a valid input.", args[1]));
					return true;
				}
				
				//Run debug command
				if (args[0].equalsIgnoreCase("debug")) {
					system.setShowDebugOutput(Boolean.valueOf(args[1].toLowerCase()));
					system.messageCommandSender(sender, String.format("Set show-debug-text to %s.", args[1].toUpperCase()));
					return true;
				}
				
				//Otherwise it's color
				system.setColorCodesEnabled(Boolean.valueOf(args[1].toLowerCase()));
				system.messageCommandSender(sender, String.format("Set enable-color-codes to %s.", args[1].toUpperCase()));
				return true;
			}
		}
		
		//Runs /chsys set PLAYER ROLE
		if (args.length == 3 && args[0].equalsIgnoreCase("set")) {
			Player player = this.getPlayer(args[1]);
			Role role = ChatSystem.Role.getRoleByString(args[2]);
			
			//Verify inputs
			if (role == Role.ALL || role == Role.CONSOLE) {
				system.messageCommandSender(sender, ChatColor.RED + String.format("Players are not permitted to use the %s role.", role.toString()));
				return true;
			} 
			else if (player != null && role != null) {
				system.addPlayer(player.getUniqueId(), role);
				system.messageCommandSender(sender, String.format("%s was moved to the %s role.", player.getName(), role.toString()));
				system.messagePlayer(null, player, String.format("Your chat role has been updated to %s.", role.toString()));
				return true;
			}
			else {
				system.messageCommandSender(sender, String.format("Failed to set %s as the %s role.", args[1], args[2]));
				return true;
			}
		}
		
		//more commands to do later -- gotta catch the bus lmao
		
		return false;
	}

}
