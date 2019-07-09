package com.gmail.jpk.stu.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.arena.GlobalW.ErrorMsgs;

/**
 * <b>Allows a player to view their current role, or let's them see info about other roles.</b><br/>
 * <ul>
 * <li> /role: show the player their current role (if any); </li>
 * <li> /role [ROLE]: assigns a player to that role; </li>
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
		//Validate the CommandSender is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage(ErrorMsgs.NOT_PLAYER.getMessage());
			return true;
		}
		
		//Process the command
		int length = args.length;
		
		//Returns information about various roles to the player
		if (length == 3 && args[1].equalsIgnoreCase("help")) {
			String role = args[2];
			PlayerRole.showRoleDescription(role, sender);
			return true;
		}
		
		//Validate the player is in the Arena
		Player player = (Player) sender;
		
		if (GlobalW.getArenaPlayer(player) == null) {
			player.sendMessage(ErrorMsgs.NOT_ARENA_PLAYER.getMessage());
			return true;
		}
		
		//Grab the player in the arena
		ArenaPlayer arena_player = GlobalW.getArenaPlayer(player);
		PlayerRole player_role   = arena_player.getClassRole();
		
		//Shows the player their current role
		if (length == 0) {
			sender.sendMessage("You're current role is " + player_role + ".");
			return true;
		}
		
		//Let's the player choose their role.
		if (length == 2) {
			//Check the player already has a role.
			if (player_role != PlayerRole.SPECTATOR) {
				sender.sendMessage("You already have a role. Please quit your current role to join a new one.");
				return true;
			}
			
			//Assign the player that role
			arena_player.setClassRole(PlayerRole.getRoleByString(args[1].toUpperCase()));
			
			return true;
		}
		return false;
	}
}
