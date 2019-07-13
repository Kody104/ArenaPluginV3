package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.tasks.CountdownTask;
import com.gmail.jpk.stu.tasks.StartArenaTask;

public class ReadyCommand extends BasicCommand {

	private static boolean all_ready;
	
	public ReadyCommand(ArenaPlugin plugin) {
		super(plugin);
		
		setAllReady(false);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Get the CommandSender as an ArenaPlayer
		ArenaPlayer arena_player = getArenaPlayer(sender);
		
		//Validate the CommandSender is an ArenaPlayer
		if (arena_player == null) {
			//If not, then exit.
			return true;
		}
		
		//Grab player info
		Player player = (Player) sender;
		String display_name = player.getName();
		
		//Grab the player's role and preferred name.
		PlayerRole player_role  = arena_player.getClassRole();
		String player_role_name = player_role.getPreferredName();
		
		//If the player is a spectator, they shouldn't be able to join.
		if (player_role == PlayerRole.SPECTATOR) {
			GlobalW.toPlayer(player, "You must choose a role before entering the arena!");
			return true;
		} 
		
		//If the player has already readied-up, un-ready them.
		if (arena_player.isReady()) {
			arena_player.setReady(false);
			GlobalW.toArenaPlayers(GlobalW.getChatTag() + String.format("%s the %s is no longer ready!", display_name, player_role_name));
		} else {
			//Ready the player and alert other ArenaPlayers.
			arena_player.setReady(true);
			GlobalW.toArenaPlayers(GlobalW.getChatTag() + String.format("%s the %s is ready to fight!", display_name, player_role_name));
		}
		
		
		//Check if all players are ready.
		int ready = 0;
		int total = GlobalW.getPlayersInArena().size();
		
		for (ArenaPlayer ap : GlobalW.getPlayersInArena()) {
			if (ap.isReady()) {
				ready++;
			}
		}
		
		//Show ArenaPlayers the ready count.
		GlobalW.toArenaPlayers(GlobalW.getChatTag() + String.format("There are (%d/%d) players ready!", ready, total));
		
		//TODO: Auto-start round if all players are ready.
		if (ready == total && !all_ready) {
			setAllReady(true);
			GlobalW.toArenaPlayers(GlobalW.getChatTag() + ChatColor.GREEN + "All players are ready! The arena will begin shortly!");
			//GlobalW.teleArenaPlayers(null); --> implement when we have actual locations
			new CountdownTask("Arena Begins in ", true, 5).runTaskLater(plugin, 15000);
			new StartArenaTask().runTaskLater(plugin, 15000);
		}		
		
		return true;
	}
	
	public static boolean allReady() {
		return all_ready;
	}
	
	public static void setAllReady(boolean all_ready) {
		ReadyCommand.all_ready = all_ready;
	}

}
