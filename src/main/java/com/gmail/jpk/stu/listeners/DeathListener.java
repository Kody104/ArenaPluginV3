package com.gmail.jpk.stu.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

/**
 * Listens for deaths that occur in the arena.
 *
 */
public class DeathListener extends BasicListener {

	public DeathListener(Plugin plugin) {
		super(plugin);
	}
	
	/**
	 * Handles LivingEntities deaths (but not ArenaPlayers).
	 * @param e the event that triggered this method
	 */
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e) {

	}
	
	/**
	 * Handles ArenaPlayer's deaths. ArenaPlayer should not drop their EXP.
	 * @param e the event that triggered this method
	 */
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {

	}

}
