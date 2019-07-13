package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.Entities.PlayerRole;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.arena.GlobalW.ErrorMsgs;

public class JoinCommand extends BasicCommand{
	
	public JoinCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) { // If sender is not a player
			sender.sendMessage(ErrorMsgs.NOT_PLAYER.getMessage());
			return true;
		}
		Player p = (Player) sender;
		if(GlobalW.getArenaPlayer(p) == null) { // If the player doesn't exist in the arena
			if(GlobalW.getRound() == 0) { // If the arena hasn't started
				if(GlobalW.getPlayersInArena().size() + 1 > GlobalW.getMaxSize()) { // If adding this player is over the player limit
					GlobalW.toPlayer(p, "The arena is currently full!");
					return true;
				}
				p.getInventory().clear(); // No outside items allowed!
				p.setFoodLevel(10); // Too much food meter = healing. NO FREE HEALING!
				GlobalW.toPlayer(p, "You have joined the arena as a spectator!");
				GlobalW.toPlayer(p, "Type " + ChatColor.GOLD + "/role all " + ChatColor.WHITE + "to see a list of current roles.");
				GlobalW.toArenaPlayers(GlobalW.getChatTag() + ChatColor.YELLOW + p.getName() + " has joined the arena!");
				GlobalW.getPlayersInArena().add(new ArenaPlayer(p, PlayerRole.SPECTATOR));
			}
			else { // Arena has started
				GlobalW.toPlayer(p, "The arena is currently in progress!");
			}
		}
		else { // The player already exists in the arena
			GlobalW.toPlayerError(p, "You are already in the arena!");
		}
		return true;
	}
}
