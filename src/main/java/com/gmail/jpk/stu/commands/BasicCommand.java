package com.gmail.jpk.stu.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.gmail.jpk.stu.arena.ArenaPlugin;

public abstract class BasicCommand implements CommandExecutor {

	private ArenaPlugin plugin;
	
	public BasicCommand(ArenaPlugin plugin) {
		this.plugin = plugin;
	}
	
	public abstract boolean performCommand(CommandSender sender, String args[]);
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String lbl, String[] args) {
		return performCommand(sender, args);
	}
	
	public ArenaPlugin getPlugin() {
		return plugin;
	}

}
