package com.gmail.jpk.stu.commands;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem;

public class DevCommand extends ChannelCommand {

	public DevCommand(ArenaPlugin plugin) {
		super(plugin, ChatSystem.Role.DEV);
	}
}
