package com.gmail.jpk.stu.listeners;

import java.util.UUID;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.arena.ChatSystem;
import com.gmail.jpk.stu.arena.ChatSystem.Role;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.tasks.DelayedMessageTask;

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
	 * Handles when a player chats on the server.
	 * @param e the event
	 */
	@EventHandler
	public void playerChatEvent(AsyncPlayerChatEvent e) {
		String message = e.getMessage();
		GlobalW.getChatSystem().sendPlayerChat(e.getPlayer(), "%s", message);
		e.setCancelled(true);
	}
		
	/**
	 * Handles when a Player joins the server
	 * @param e the event
	 */
	@EventHandler
	public void playerJoinServerEvent(PlayerJoinEvent e) {
		Player player = e.getPlayer();
		UUID uid = player.getUniqueId();
		ChatSystem system = GlobalW.getChatSystem();
		Plugin plugin = GlobalW.getPlugin();
		String tag = GlobalW.getChatTag();
		int delay = 20; //(in ticks)
		
		//Add the player if they aren't in the system.
		if (!system.contains(uid)) {
			system.addPlayer(uid, Role.PLAYER);
			new DelayedMessageTask(player, tag + String.format("Welcome to the server %s! Type" + ChatColor.GOLD + " /join " + ChatColor.WHITE +"to join the Arena.", player.getName())).runTaskLater(plugin, delay);
			return;
		}
		
		new DelayedMessageTask(player, tag + String.format("Welcome back %s!", player.getName())).runTaskLater(plugin, delay);
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
