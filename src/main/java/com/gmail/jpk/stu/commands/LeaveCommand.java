package com.gmail.jpk.stu.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.arena.GlobalW.ErrorMsgs;

public class LeaveCommand extends BasicCommand {

	public LeaveCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		if(!(sender instanceof Player)) { // If sender is not player
			sender.sendMessage(ErrorMsgs.NOT_PLAYER.getMessage());
			return true;
		}
		Player p = (Player) sender;
		if(GlobalW.getArenaPlayer(p) != null) { // If the player exists inside the arena
			if(GlobalW.getRound() != 0) { // If the arena has already started
				GlobalW.toPlayer(p, "No abandoning your team after the arena has started!");
				return true;
			}
			p.setFoodLevel(20); // Max out food level
			p.setExp(0); //Remove any experience
			GlobalW.toPlayer(p, "You have left the arena!");
			GlobalW.removeArenaPlayer(p);
			GlobalW.toArenaPlayers(GlobalW.getChatTag() + ChatColor.YELLOW + p.getName() + " has left the arena!");
			return true;
		}
		else {
			GlobalW.toPlayerError(p, ErrorMsgs.NOT_ARENA_PLAYER.getMessage());
			return true;
		}
	}
}
