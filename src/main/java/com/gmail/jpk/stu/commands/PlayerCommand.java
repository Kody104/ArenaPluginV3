package com.gmail.jpk.stu.commands;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem.Role;

public class PlayerCommand extends ChannelCommand {

	public PlayerCommand(ArenaPlugin plugin) {
		super(plugin, Role.PLAYER);
	}

}
