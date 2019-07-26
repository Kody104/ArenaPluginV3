package com.gmail.jpk.stu.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
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
	 * Prevents Creatures from spawning unless the plugin asks it to.
	 * @param e the event
	 */
	@EventHandler
	public void onCreatureSpawnEvent(CreatureSpawnEvent e) {
		//If the creature isn't spawn by this plugin, then cancel it.
		if (e.getSpawnReason() != SpawnReason.CUSTOM) {
			e.setCancelled(true);
		}
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
		ArenaCreature creature = GlobalW.getArenaCreature(entity);
		
		//Don't drop exp or items
		e.getDrops().clear();
		e.setDroppedExp(0);
		
		//See if the round has ended
		if (creature != null) {
			GlobalW.removeArenaCreature(entity);
			
			//Have all the creatures been killed?
			if (GlobalW.getCreaturesInArena().isEmpty()) {
				int round = GlobalW.getRound();
				int exp = (int) (1 + (round / 10)); //How much exp should we give at the end of a round?
				
				//Check if round is divisible by 5
				if (round % 5 == 0) {
					//teleport players to shop
				}
				
				//Grant surviving players experience.
				for (ArenaPlayer ap : GlobalW.getPlayersInArena()) {
					ap.addExp(exp, false);
				}
			}
		}
		
		//Verify a player killed this entity
		if (killer != null && GlobalW.getArenaPlayer(killer) != null) {
			ArenaPlayer player = GlobalW.getArenaPlayer(killer);
			//Verify both are in the Arena
			if (creature != null && player != null) {
				player.addExp(creature.getExpDrop(0), false);
			}
		}
	}
	
	/**
	 * Handles ArenaPlayer's deaths. ArenaPlayer should not drop their EXP.
	 * High priority as this needs to ran as soon as possible to remove player from list of arena players.
	 * @param e the event that triggered this method
	 */
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
		Player player = (Player) e.getEntity();
		
		if (GlobalW.getArenaPlayer(player) != null) {
			e.setDroppedExp(0);
			e.setDeathMessage(ChatColor.RED + String.format("%s has died! They've been removed from the arena!", player.getName()));
			player.getInventory().clear();
			GlobalW.removeArenaPlayer(player);
		}
	}
}
