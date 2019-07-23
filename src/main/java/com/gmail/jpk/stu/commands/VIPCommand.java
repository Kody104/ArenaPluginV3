package com.gmail.jpk.stu.commands;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem.Role;

public class VIPCommand extends ChannelCommand {

	public VIPCommand(ArenaPlugin plugin) {
		super(plugin, Role.VIP);
	}

}
