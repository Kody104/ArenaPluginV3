package com.gmail.jpk.stu.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
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
	 * Handles LivingEntities deaths (but not ArenaPlayers).
	 * @param e the event that triggered this method
	 */
	@EventHandler
	public void onEntityDeathEvent(EntityDeathEvent e) {
		//Note: Because only ArenaPlayers are allowed in the Arena, it should be sufficient that a LivingEntity killed by an ArenaPlayer is an ArenaCreature.
		LivingEntity entity = e.getEntity();
		Player killer = entity.getKiller();
		ArenaCreature creature = GlobalW.getArenaCreature(entity);
		
		//See if the round has ended
		if (creature != null) {
			GlobalW.removeArenaCreature(entity);
			e.getDrops().clear();
			e.setDroppedExp(0);
			
			if (GlobalW.getCreaturesInArena().isEmpty()) {
				int round = GlobalW.getRound();
				int exp = (int) (1 + (round / 10));
				ArenaPlayer player = GlobalW.getArenaPlayer(killer);
				player.addExp(exp);
				
				GlobalW.toArenaPlayers(GlobalW.getChatTag() + ChatColor.GOLD + String.format("%s Congrats! You have survived round %d! %d experience rewarded!", GlobalW.getChatTag(), round, exp));
			}
		}
		
		//Verify a player killed this entity
		if (killer != null && GlobalW.getArenaPlayer(killer) != null) {
			ArenaPlayer player = GlobalW.getArenaPlayer(killer);
			//Verify both are in the Arena
			if (creature != null && player != null) {
				player.addExp(creature.getExpDrop(0));
			}
		}
	}
	
	/**
	 * Handles ArenaPlayer's deaths. ArenaPlayer should not drop their EXP.
	 * @param e the event that triggered this method
	 */
	@EventHandler
	public void onPlayerDeathEvent(PlayerDeathEvent e) {
		Player player = (Player) e.getEntity();
		
		if (GlobalW.getArenaPlayer(player) != null) {
			player.getInventory().clear();
			GlobalW.removeArenaPlayer(player);
			GlobalW.toPlayer(player, ChatColor.RED + "You have died! You've been removed from the Arena.");
			GlobalW.toArenaPlayers(GlobalW.getChatTag() + ChatColor.RED + String.format("%s has died! They've been removed from the Arena.", player.getName()));
		}
	}
}
