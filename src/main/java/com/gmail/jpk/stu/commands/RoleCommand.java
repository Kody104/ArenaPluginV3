package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;

/**
 * <b>Allows a player to view their current role, or let's them see info about other roles.</b><br/>
 * <ul>
 * <li> /role: show the player their current role (if any); </li>
 * <li> /role all: shows the player all the available roles; </li>
 * <li> /role [ROLE]: assigns a player to that role OR returns the role a target player; </li>
 * <li> /role about [ROLE]: gives the player information about that role. </li>
 * </ul>
 * @author ASquanchyPenguin
 */
public class RoleCommand extends BasicCommand {

	public RoleCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Get necessary info
		ArenaPlayer arena_player = getArenaPlayer(sender);
		
		//The console should use this command
		if (arena_player == null) {
			sender.sendMessage(GlobalW.getChatTag() + "This command isn't available through the console.");
			return true;
		}
		
		//Grab the player's role
		int length = args.length;
		Player player = (Player) sender;
		PlayerRole player_role = arena_player.getClassRole(); 
		String player_role_name = player_role.getName();
		
		//Runs /role
		if (length == 0) {
			GlobalW.toPlayer(player, "Your current role is " + player_role_name + ".");
			return true;
		}
		
		//Runs /role all
		if (length == 1 && args[0].equalsIgnoreCase("all")) {
			GlobalW.toPlayer(player, "Here is a list of the current roles in the arena.");
			String line = "";
			
			//Print out the Roles
			int i = 1;
			
			for (PlayerRole role : PlayerRole.values()) {
				line = String.format("%d. %s: %s", i++, role.getName(), role.getDescription());
				player.sendMessage(line);
			}
			
			return true;
		}
		
		//Runs /role ROLE|PLAYER
		if (length == 1) {
			PlayerRole desired = PlayerRole.getRoleByString(args[0]);
			Player target = this.getPlayer(args[0]);
			
			//Check if player has a role already
			if (desired != null && player_role != null && player_role != PlayerRole.SPECTATOR && target == null) {
				GlobalW.toPlayer(player, String.format("You are already a %s. Use %s to quit your current role.", player_role_name, ChatColor.GOLD + "/quit" + ChatColor.RESET));
				return true;
			}
			
			//Check if player doesn't have a role yet
			else if (desired != null && target == null) {
				//Verify they didn't pick spectator
				if (desired == PlayerRole.SPECTATOR) {
					GlobalW.toPlayer(player, "You can't choose this role.");
					return true;
				}
				
				arena_player.setClassRole(desired);
				GlobalW.toArenaPlayers(String.format("%s has chosen to be a %s!", player.getName(), desired.getName()));
				return true;
			}
			
			//Check if they are looking for a Player's role
			else if (desired == null && target != null) {
				ArenaPlayer target2 = GlobalW.getArenaPlayer(target);
				
				//Verify target2 is an ArenaPlayer
				if (target2 != null) {
					PlayerRole target_role = target2.getClassRole();
					
					//Send the message depending if the target has a role
					if (target_role != null) {
						GlobalW.toPlayer(player, String.format("%s is a %s.", target.getName(), target_role.getName()));
						return true;
					} else {
						GlobalW.toPlayer(player, String.format("%s doesn't have a role yet.", target.getName()));
						return true;
					}
				}
			}
			
			//If they get here, they've messed up the command.
			else {
				GlobalW.toPlayerError(player, ChatColor.RED + String.format("%s is not a valid argument.", args[0]));
				return true;
			}
		}
		
		//Runs /role about [ROLE]
		if (length == 2 && args[0].equalsIgnoreCase("about")) {
			PlayerRole desired = PlayerRole.getRoleByString(args[1]);
			
			//Verify this is a proper role and send
			if (desired != null) {
				GlobalW.toPlayer(player, String.format("%s is %s", desired.getName(), desired.getDescription()));
				return true;
			} 
			
			//Couldn't find the role
			else {
				GlobalW.toPlayer(player, String.format("There is no %s role. Try %s for a list of all the roles.", args[1], ChatColor.GOLD + "/role all" + ChatColor.RESET));
				return true;
			}
		}
		
		return false;
	}
}