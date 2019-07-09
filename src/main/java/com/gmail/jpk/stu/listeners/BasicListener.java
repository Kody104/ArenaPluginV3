package com.gmail.jpk.stu.listeners;

import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

public abstract class BasicListener implements Listener {

	private Plugin plugin;
	
	/**
	 * Creates a BasicListener object.
	 * @param plugin the plugin this listener is observing.
	 */
	public BasicListener(Plugin plugin) {
		this.setPlugin(plugin);
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}
	
	public Plugin getPlugin() {
		return plugin;
	}
	
	public void setPlugin(Plugin plugin) {
		this.plugin = plugin;
	}
	
}
