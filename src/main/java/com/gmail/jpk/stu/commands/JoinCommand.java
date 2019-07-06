package com.gmail.jpk.stu.commands;

import org.bukkit.command.CommandSender;

import com.gmail.jpk.stu.arena.ArenaPlugin;

public class JoinCommand extends BasicCommand{
	
	public JoinCommand(ArenaPlugin plugin) {
		super(plugin);
	}

	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		return false;
	}
}
