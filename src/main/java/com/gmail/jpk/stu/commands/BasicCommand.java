package com.gmail.jpk.stu.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.arena.GlobalW.ErrorMsgs;

public abstract class BasicCommand implements CommandExecutor {

	protected ArenaPlugin plugin;
	
	public BasicCommand(ArenaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public abstract boolean performCommand(CommandSender sender, String args[]);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		return performCommand(sender, args);
	}
	
	/**
	 * <b>Returns a valid CommandSender back as an ArenaPlayer.</b><br/>
	 * If the CommandSender isn't an ArenaPlayer, then it returns null.
	 * @param sender the CommandSender
	 * @return The CommandSender as an ArenaPlayer. 
	 */
	protected ArenaPlayer getArenaPlayer(CommandSender sender) {
		//Validate the CommandSender is a player
		if (!(sender instanceof Player)) {
			sender.sendMessage(ErrorMsgs.NOT_PLAYER.getMessage());
			return null;
		}
		
		Player player = (Player) sender;
		
		if (GlobalW.getArenaPlayer(player) == null) {
			player.sendMessage(ErrorMsgs.NOT_ARENA_PLAYER.getMessage());
			return null;
		}
		
		return GlobalW.getArenaPlayer(player);
	}
	
	public ArenaPlugin getPlugin() {
		return plugin;
	}

}
