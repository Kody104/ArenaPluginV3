package com.gmail.jpk.stu.arena;
import org.bukkit.plugin.java.JavaPlugin;

import com.gmail.jpk.stu.commands.ArenaCommand;
import com.gmail.jpk.stu.commands.DevCommand;
import com.gmail.jpk.stu.commands.GiveAbilityItemCommand;
import com.gmail.jpk.stu.commands.GiveCustomItemCommand;
import com.gmail.jpk.stu.commands.JoinCommand;
import com.gmail.jpk.stu.commands.LeaveCommand;
import com.gmail.jpk.stu.commands.QuitCommand;
import com.gmail.jpk.stu.commands.ReadyCommand;
import com.gmail.jpk.stu.commands.RoleCommand;
import com.gmail.jpk.stu.commands.VIPCommand;
import com.gmail.jpk.stu.listeners.DeathListener;
import com.gmail.jpk.stu.listeners.ItemInteractionListener;
import com.gmail.jpk.stu.listeners.PlayerCastListener;
import com.gmail.jpk.stu.listeners.PlayerServerInteractionListener;
import com.gmail.jpk.stu.listeners.UndroppableSpecialItemListener;

public class ArenaPlugin extends JavaPlugin {
	
	private final String ver = "0.0.1";
	
	@Override
	public void onEnable() {
		logMessage("Enabling [Arena Ver " + ver + "]!");
		
		logMessage("Attempting initalization of GlobalW class.");
		GlobalW.initialize(this);
		
		logMessage("Attempting to load all program commands.");
		this.getCommand("arena").setExecutor(new ArenaCommand(this));
		this.getCommand("dev").setExecutor(new DevCommand(this));
		this.getCommand("gci").setExecutor(new GiveCustomItemCommand(this));
		this.getCommand("join").setExecutor(new JoinCommand(this));
		this.getCommand("leave").setExecutor(new LeaveCommand(this));
		this.getCommand("ready").setExecutor(new ReadyCommand(this));
		this.getCommand("role").setExecutor(new RoleCommand(this));
		this.getCommand("quit").setExecutor(new QuitCommand(this));
		this.getCommand("gai").setExecutor(new GiveAbilityItemCommand(this));
		this.getCommand("vip").setExecutor(new VIPCommand(this));
		
		logMessage("Attemping to load all program Listeners.");
		new UndroppableSpecialItemListener(this);
		new DeathListener(this);
		new PlayerCastListener(this);
		new ItemInteractionListener(this);
		new PlayerServerInteractionListener(this);
		
		logMessage("Set-up complete.");
	}
	
	@Override
	public void onDisable() {
		logMessage("Disabling Arena!");
		ItemInteractionListener.clearDroppedItems();
		GlobalW.terminate();
	}
	
	public void logMessage(String message) {
		getLogger().info(message);
	}
}
