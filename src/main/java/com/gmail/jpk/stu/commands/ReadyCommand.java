package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;

/**
 * 
 *
 */
public class ReadyCommand extends BasicCommand {

	public ReadyCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Get the CommandSender as an ArenaPlayer
		ArenaPlayer arena_player = getArenaPlayer(sender);
		
		//Validate the CommandSender is an ArenaPlayer
		if (arena_player == null) {
			//If they aren't, exit.
			return true;
		}
		
		//Grab the player's role and preferred name.
		PlayerRole player_role  = arena_player.getClassRole();
		String player_role_name = player_role.getPreferredName();
		
		//If the player is a spectator, they shouldn't be able to join.
		if (player_role == PlayerRole.SPECTATOR) {
			sender.sendMessage("You must choose a role before entering the arena!");
			return true;
		} 
		
		//If the player has already readied-up, they can't back out.
		//QUESTION: Or would it be preferred that a player can back out?
		if (arena_player.isReady()) {
			sender.sendMessage("You've already readied up!");
			return true;
		}
		
		//Ready the player and alert other ArenaPlayers.
		Player player = (Player) sender;
		String display_name = player.getName();
		arena_player.setReady(true);
		GlobalW.toArenaPlayers(ChatColor.GOLD + String.format("%s the %s is ready to fight!", display_name, player_role_name));
		
		return false;
	}
	
	

}
