package com.gmail.jpk.stu.listeners;

import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.arena.GlobalW;

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
		//Note: Because only ArenaPlayers are allowed in the Arena, it should be sufficient that a LivingEntity killed by an ArenaPlayer is an ArenaCreature.
		LivingEntity entity = e.getEntity();
		Player killer = entity.getKiller();
		
		//Verify a player killed this entity
		if (killer != null && GlobalW.getArenaPlayer(killer) != null) {
			//TODO: Stuff. Gotta add some base code in before we do anything cool.
		}
	}
	
	/**
	 * Handles ArenaPlayer's deaths. ArenaPlayer should not drop their EXP.
	 * @param e the event that triggered this method
	 */
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {

	}

}
