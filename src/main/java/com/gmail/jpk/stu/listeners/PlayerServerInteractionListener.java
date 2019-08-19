package com.gmail.jpk.stu.listeners;

import java.util.UUID;
import java.util.logging.Logger;

import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.arena.ArenaPlugin;
import com.gmail.jpk.stu.arena.ChatSystem;
import com.gmail.jpk.stu.arena.ChatSystem.Role;
import com.gmail.jpk.stu.commands.ChannelCommand;
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
		ChatSystem system = GlobalW.getChatSystem();
		Player player = e.getPlayer();
		UUID uid = player.getUniqueId();
		String message = e.getMessage();
		
		e.setCancelled(true);
		
		if (ChannelCommand.getLockedPlayers().contains(uid)) {
			switch (system.getRole(uid)) {
				case DEV:
				{
					system.messageDevs(uid, message);
					return;
				}
				case VIP:
				{
					system.messageVips(uid, message);
					return;
				}
				default:	
				{
					system.messageDevs(null, String.format("Unknown condition. %s is locked, but unable to message their channel.", player.getName()));
					system.messageAll(uid, message);
					return;
				}
			}			
		}
		
		system.messageAll(uid, message);		
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
		ArenaPlugin plugin = GlobalW.getPlugin();
		Logger log = plugin.getLogger();
		
		player.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20.0d);
		
		//Add the player to ChatSystem if they aren't in it.
		if (!system.contains(uid)) {
			log.info(String.format("Couldn't find %s in the ChatSystem. They will be added as a Player.", player.getName()));
			system.addPlayer(uid, Role.PLAYER);
			return;
		}
		
		//Greet the player
		new DelayedMessageTask(player, ChatColor.GOLD + String.format("Welcome back! It's great to see you again, %s!", player.getName())).runTaskLater(plugin, 60);
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
