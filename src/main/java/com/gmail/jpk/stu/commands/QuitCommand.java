package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;

/**
 * <b>Allows a player to quit their current role -- if they have one.</b><br/>
 * <ul>
 * <li> /quit - quits the player's current role (if any) </li>
 * </ul>
 * @author ASquanchyPenguin
 */
public class QuitCommand extends BasicCommand {

	public QuitCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Get the CommandSender as an ArenaPlayer
		ArenaPlayer player = getArenaPlayer(sender);
		
		//Validate the ArenaPlayer
		if (player == null) {
			//If they aren't, exit.
			return true;
		}
		
		//Get the player's current role
		PlayerRole player_role = player.getClassRole();
		String player_role_name = player_role.getPreferredName();
		
		//Verify they have a role and change their role to spectator if so.
		if (player_role == PlayerRole.SPECTATOR) {
			sender.sendMessage("You haven't chosen a role yet! Try " + ChatColor.GOLD +" \"/role all\" " + ChatColor.WHITE +"to view all available roles.");
			return true;
		} else {
			player.setClassRole(PlayerRole.SPECTATOR);
			sender.sendMessage("You have quit the " + player_role_name + " role. You are now a spectator.");
			return true;
		}		
	}
}
