package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;

/**
 * <b>Allows a player to view their current role, or let's them see info about other roles.</b><br/>
 * <ul>
 * <li> /role: show the player their current role (if any); </li>
 * <li> /role [ROLE]: assigns a player to that role; </li>
 * <li> /role all: shows the player all the available roles; </li>
 * <li> /role help [ROLE]: gives the player information about that role. </li>
 * </ul>
 * @author ASquanchyPenguin
 */
public class RoleCommand extends BasicCommand {

	public RoleCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Get the CommandSender as an ArenaPlayer
		ArenaPlayer arena_player = getArenaPlayer(sender);
		
		//Validate the CommandSender is an ArenaPlayer
		if (arena_player == null) {
			sender.sendMessage(GlobalW.ErrorMsgs.NOT_ARENA_PLAYER.getMessage());
			return true;
		}
		
		//Argument Length
		int length = args.length;
		
		//Grab the player's role
		PlayerRole player_role = arena_player.getClassRole(); 
		String player_role_name = arena_player.getClassRole().getPreferredName();
		
		//Returns information about various roles to the player
		if (length == 2 && args[0].equalsIgnoreCase("HELP")) {
			String[] description = arena_player.getClassRoleDescription(args[1]);
			
			for (String string : description) {
				sender.sendMessage(string);
			}
			
			return true;
		}
		
		//Let's the player choose their role.
		if (length == 1) {
			//Show the player all of the roles for /role all
			if (args[0].equalsIgnoreCase("ALL")) {
				//Create a string with all roles
				String roles = "";
				
				//Get all the roles
				for (PlayerRole pr : PlayerRole.values()) {
					roles = roles.concat(pr.getPreferredName() + ", ");
				}
				
				//Remove the trailing comma
				roles = roles.substring(0, roles.length() - 2) + ".";

				//Send the message
				sender.sendMessage("Here are the current available roles:");
				sender.sendMessage("");
				sender.sendMessage("> " + roles);
				sender.sendMessage("");
				sender.sendMessage("Type" + ChatColor.GOLD +" \"/role help [ROLE]\"" + ChatColor.WHITE + " for more information about a role.");
				
				return true;
			}
			
			//Get the requested PlayerRole. 
			PlayerRole requested_role = PlayerRole.getRoleByString(args[0].toUpperCase());
			
			//Verify the role isn't null.
			if (requested_role == null) {
				sender.sendMessage("Couldn't find the \"" + args[0] + "\" role. Try " + ChatColor.GOLD + "\"role all\" " + ChatColor.WHITE + "to see a list of available roles.");
				return true;
			}
			
			//Check the player already has a role.
			if (player_role != PlayerRole.SPECTATOR) {
				sender.sendMessage("You already a " + player_role_name + ". Please quit your current role to join a new one.");
				return true;
			}

			//Assign the player that role
			arena_player.setClassRole(requested_role);
			player_role_name = arena_player.getClassRole().getPreferredName();
			sender.sendMessage("You are now a " + player_role_name + ".");
			return true;
		}
				
		//Shows the player their current role
		if (length == 0) {
			sender.sendMessage("Your current role is " + player_role_name + ".");
			return true;
		}
		
		return false;
	}
}