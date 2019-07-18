package com.gmail.jpk.stu.listeners;

import org.bukkit.entity.Creature;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.abilities.AbilityTarget;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.items.AbilityItem;

public class PlayerCastListener extends BasicListener {

	public PlayerCastListener(Plugin plugin) {
		super(plugin);
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		
		ArenaPlayer player = GlobalW.getArenaPlayer(p);
		
		// Verify that this is an arenaplayer
		if(player != null) {
			// Is the player holding an ability item
			if(player.isHoldingAbilityItem()) {
				AbilityItem item = player.getAbilityItemInHand();
				// If the entity is a player
				if(e.getRightClicked() instanceof Player) {
					Player otherP = (Player) e.getRightClicked();
					ArenaPlayer target_player = GlobalW.getArenaPlayer(otherP);
					//TODO: Check for AbilityTarget.TargetTypes
				}
				// If the entity is a living entity
				else if(e.getRightClicked() instanceof LivingEntity) {
					LivingEntity le = (LivingEntity) e.getRightClicked();
					ArenaCreature creature = GlobalW.getArenaCreature(le);
					
					// If creature is an arenacreature 
					if(creature != null) {
						//TODO:WORKING
					}
				}
			}
		}
	}

}
