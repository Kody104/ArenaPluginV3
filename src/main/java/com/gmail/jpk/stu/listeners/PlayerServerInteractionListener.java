package com.gmail.jpk.stu.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.arena.GlobalW;

/**
 * Listens for various interactions between Players and the Server
 */
public class PlayerServerInteractionListener extends BasicListener {

	public PlayerServerInteractionListener(Plugin plugin) {
		super(plugin);
	}
	
	/**
	 * Removes the Player from the Arena. Exclusive to this class.
	 * @param player the player to remove.
	 */
	private void removePlayer(Player player) {		
		if (GlobalW.getArenaPlayer(player) != null) {
			GlobalW.removeArenaPlayer(player);
		}
	}
		
	/**
	 * Handles when a Player joins the server
	 * @param e the event
	 */
	@EventHandler
	public void playerJoinServerEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		
		GlobalW.toPlayer(player, "Welcome! To join the Arena, type " + ChatColor.GOLD +"/join.");
	}
	
	/**
	 * Handles when a Player is kicked from the server 
	 * @param e the event
	 */
	@EventHandler
	public void playerKickEvent(PlayerKickEvent e) {
		removePlayer(e.getPlayer());
	}
	
	/**
	 * Handles when a Player quits the server
	 * @param e the event
	 */
	@EventHandler
	public void playerQuitServerEvent(PlayerQuitEvent e) {
		removePlayer(e.getPlayer());
	}

}
