package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;

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
		ArenaPlayer arena_player = getArenaPlayer(sender);
		
		//Validate the ArenaPlayer
		if (arena_player == null) {
			//If they aren't, exit.
			return true;
		}
		
		//Get the ArenaPlayer as a Player
		Player player = (Player) sender;
		
		//Player shouldn't be able to perform this command if they have readied for the arena.
		if (arena_player.isReady()) {
			GlobalW.toPlayer(player, "You have already readied up! You are unable to use this command at this time.");
			return true;
		}
		
		//Similarly, they shouldn't be able to abandon their role if they're in the middle of a fight.
		if (GlobalW.isHasStarted()) {
			GlobalW.toPlayer(player, "You are unable to quit your role in the middle of battle!");
			return true;
		}
		
		//Get the player's current role
		PlayerRole player_role = arena_player.getClassRole();
		String player_role_name = player_role.getPreferredName();
		
		//Verify they have a role and change their role to spectator if so.
		if (player_role == PlayerRole.SPECTATOR) {
			GlobalW.toPlayer(player, "You haven't chosen a role yet! Try " + ChatColor.GOLD + "/role all " + ChatColor.WHITE + "to view all available roles.");
			return true;
		} else {
			arena_player.setClassRole(PlayerRole.SPECTATOR);
			GlobalW.toPlayer(player, "You have quit the " + player_role_name + " role. You are now a spectator.");
			arena_player.setReady(false); //Prevents players from auto-readying after quitting
			return true;
		}		
	}
}
