package com.gmail.jpk.stu.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.arena.ChatSystem.Role;

public abstract class ChannelCommand extends BasicCommand {

	protected ChatSystem.Role role;
	
	public ChannelCommand(ArenaPlugin plugin, Role role) {
		super(plugin);
		
		setRole(role);
	}
	
	@Override
	public boolean performCommand(CommandSender sender, String[] args) {
		//Verify length
		if (args.length == 0) {
			return false;
		}
		
		//Get Message
		String message = this.getMessage(args);
		
		//Handle console
		if (sender instanceof ConsoleCommandSender) {
			GlobalW.getChatSystem().messageChannelFromConsole(role, "%s", message);
			return true;
		}
		
		else if (sender instanceof Player) {
			Player player = (Player) sender;
			GlobalW.getChatSystem().sendPlayerChatToRole(player, role, "%s", message);
			return true;
		}
		
		
		return false;
	}
	
	/**
	 * Creates a message from a string of input
	 * @param args the arguments forming the message
	 * @return the trimmed message
	 */
	protected String getMessage(String[] args) {
		String message = "";
		
		for (int i = 0; i < args.length; i++) {
			message = args[i] + " ";
		}
		
		return message.trim();
	}
	
	public ChatSystem.Role getRole() {
		return role;
	}
	
	public void setRole(Role role) {
		this.role = role;
	}
}
