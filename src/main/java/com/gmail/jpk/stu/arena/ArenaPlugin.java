package com.gmail.jpk.stu.arena;
import org.bukkit.plugin.java.JavaPlugin;

public class ArenaPlugin extends JavaPlugin {
	
	private final String ver = "0.0.1";
	
	@Override
	public void onEnable() {
		logMessage("Enabling [Arena Ver " + ver + "]!");
	}
	
	@Override
	public void onDisable() {
		logMessage("Disabling Arena!");
	}
	
	public void logMessage(String message) {
		getLogger().info(message);
	}
}
