package com.gmail.jpk.stu.arena;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.commands.JoinCommand;
import com.gmail.jpk.stu.commands.LeaveCommand;

public class ArenaPlugin extends JavaPlugin {
	
	private final String ver = "0.0.1";
	
	@Override
	public void onEnable() {
		logMessage("Enabling [Arena Ver " + ver + "]!");
		this.getCommand("join").setExecutor(new JoinCommand(this));
		this.getCommand("leave").setExecutor(new LeaveCommand(this));
	}
	
	@Override
	public void onDisable() {
		logMessage("Disabling Arena!");
	}
	
	public void logMessage(String message) {
		getLogger().info(message);
	}
}
