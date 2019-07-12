package com.gmail.jpk.stu.arena;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.commands.JoinCommand;
import com.gmail.jpk.stu.commands.LeaveCommand;
import com.gmail.jpk.stu.commands.QuitCommand;
import com.gmail.jpk.stu.commands.ReadyCommand;
import com.gmail.jpk.stu.commands.RoleCommand;
import com.gmail.jpk.stu.listeners.UndroppableSpecialItemListener;

public class ArenaPlugin extends JavaPlugin {
	
	private final String ver = "0.0.1";
	
	@Override
	public void onEnable() {
		logMessage("Enabling [Arena Ver " + ver + "]!");
		
		logMessage("Attempting initalization of GlobalW class.");
		GlobalW.initialize(this);
		
		logMessage("Attempting to load all program commands.");
		this.getCommand("join").setExecutor(new JoinCommand(this));
		this.getCommand("leave").setExecutor(new LeaveCommand(this));
		this.getCommand("ready").setExecutor(new ReadyCommand(this));
		this.getCommand("role").setExecutor(new RoleCommand(this));
		this.getCommand("quit").setExecutor(new QuitCommand(this));
		
		logMessage("Attemping to load all program Listeners.");
		new UndroppableSpecialItemListener(this);
		
		logMessage("Set-up complete.");
	}
	
	@Override
	public void onDisable() {
		logMessage("Disabling Arena!");
	}
	
	public void logMessage(String message) {
		getLogger().info(message);
	}
}
