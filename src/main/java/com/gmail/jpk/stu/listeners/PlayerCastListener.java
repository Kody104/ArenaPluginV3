package com.gmail.jpk.stu.listeners;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.plugin.Plugin;

import com.gmail.jpk.stu.Entities.ArenaCreature;
import com.gmail.jpk.stu.Entities.ArenaEntity;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.abilities.AbilityTarget;
import com.gmail.jpk.stu.arena.GlobalW;
import com.gmail.jpk.stu.items.AbilityItem;

public class PlayerCastListener extends BasicListener {

	public PlayerCastListener(Plugin plugin) {
		super(plugin);
	}
	
	private List<ArenaEntity> getArenaPlayersInRangeToLocation(Location loc, int range) {
		List<ArenaEntity> players = new ArrayList<ArenaEntity>();
		for(Entity ent : loc.getChunk().getEntities()) {
			LivingEntity le = (LivingEntity) ent;
			// If this is a player
			if(le instanceof Player) {
				Player p = (Player) le;
				ArenaPlayer player = GlobalW.getArenaPlayer(p);
				// Check for them being in the arena
				if(player != null) {
					players.add(player);
				}
			}
		}
		return players;
	}
	
	private List<ArenaEntity> getArenaCreaturesInRangeToLocation(Location loc, int range) {
		List<ArenaEntity> creatures = new ArrayList<ArenaEntity>();
		for(Entity ent : loc.getChunk().getEntities()) {
			// If the entity is alive and also within our ability's range of effect
			if(ent instanceof LivingEntity && loc.distance(ent.getLocation()) <= range) {
				LivingEntity le = (LivingEntity) ent;
					ArenaCreature creature = GlobalW.getArenaCreature(le);
					// Check for them being in the arena
					if(creature != null) {
						creatures.add(creature);
					}
				}
			}
		return creatures;
	}
	
	private List<ArenaEntity> getArenaEntitiesInRangeToLocation(Location loc, int range) {
		List<ArenaEntity> entities = new ArrayList<ArenaEntity>();
		// Get all entities in loaded chunk
		for(Entity ent : loc.getChunk().getEntities()) {
			// If the entity is alive and also within our ability's range of effect
			if(ent instanceof LivingEntity && loc.distance(ent.getLocation()) <= range) {
				LivingEntity le = (LivingEntity) ent;
				// If this is a player
				if(le instanceof Player) {
					Player p = (Player) le;
					ArenaPlayer player = GlobalW.getArenaPlayer(p);
					// Check for them being in the arena
					if(player != null) {
						entities.add(player);
					}
				}
				// This is definitely not a player
				else {
					ArenaCreature creature = GlobalW.getArenaCreature(le);
					// Check for them being in the arena
					if(creature != null) {
						entities.add(creature);
					}
				}
			}
		}
		return entities;
	}
	
	private ArenaPlayer getClosestArenaPlayerToLocation(Location loc) {
		ArenaPlayer chosen = null; // The entity to return
		Location best = null; // The location of the entity above
		for(Entity ent : loc.getChunk().getEntities()) { // For all entities on chunk
			if(ent instanceof LivingEntity) { // Are they alive?
				LivingEntity le = (LivingEntity) ent;
				// If the entity is a player
				if(le instanceof Player) {
					ArenaPlayer player = GlobalW.getArenaPlayer((Player)le);
					// If the player is in the arena
					if(player != null) {
						if(chosen == null) { // If this is the first cycle
							chosen = player; // Set creature
							best = le.getLocation(); // Set location
							continue;
						}
						else { // This is the 2nd or more cycle
							double chosenDiff = loc.distance(best); // The location difference between the chosen entity and the location we are trying for
							double challengeDiff = loc.distance(le.getLocation()); // The location difference between the challenger entity and the location we are trying for
							if(chosenDiff > challengeDiff) { // If the challeneger is closer to the location than the chosen
								chosen = player; // Set creature
								best = le.getLocation(); // Set location
							}
							continue;
						}
					}
				}
			}
		}
		return chosen;
	}
	
	private ArenaCreature getClosestArenaCreatureToLocation(Location loc) {
		ArenaCreature chosen = null; // The entity to return
		Location best = null; // The location of the entity above
		for(Entity ent : loc.getChunk().getEntities()) { // For all entities on chunk
			if(ent instanceof LivingEntity) { // Are they alive?
				LivingEntity le = (LivingEntity) ent;
				ArenaCreature creature = GlobalW.getArenaCreature(le);
				// If the entity is an arenacreature
				if(creature != null) {
					if(chosen == null) { // If this is the first cycle
						chosen = creature; // Set creature
						best = le.getLocation(); // Set location
						continue;
					}
					else { // This is the 2nd or more cycle
						double chosenDiff = loc.distance(best); // The location difference between the chosen entity and the location we are trying for
						double challengeDiff = loc.distance(le.getLocation()); // The location difference between the challenger entity and the location we are trying for
						if(chosenDiff > challengeDiff) { // If the challeneger is closer to the location than the chosen
							chosen = creature; // Set creature
							best = le.getLocation(); // Set location
						}
						continue;
					}
				}
			}
		}
		return chosen;
	}
	
	private ArenaEntity getClosestArenaEntityToLocation(Location loc) {
		ArenaEntity chosen = null; // The entity to return
		Location best = null; // The location of the entity above
		for(Entity ent : loc.getChunk().getEntities()) { // For all entities on chunk
			if(ent instanceof LivingEntity) { // Are they alive?
				LivingEntity le = (LivingEntity) ent;
				ArenaCreature creature = GlobalW.getArenaCreature(le);
				// If the entity is an arenacreature
				if(creature != null) {
					if(chosen == null) { // If this is the first cycle
						chosen = creature; // Set creature
						best = le.getLocation(); // Set location
						continue;
					}
					else { // This is the 2nd or more cycle
						double chosenDiff = loc.distance(best); // The location difference between the chosen entity and the location we are trying for
						double challengeDiff = loc.distance(le.getLocation()); // The location difference between the challenger entity and the location we are trying for
						if(chosenDiff > challengeDiff) { // If the challeneger is closer to the location than the chosen
							chosen = creature; // Set creature
							best = le.getLocation(); // Set location
						}
						continue;
					}
				}
				// If not
				else {
					// If the entity is a player
					if(le instanceof Player) {
						ArenaPlayer player = GlobalW.getArenaPlayer((Player)le);
						// If the player is in the arena
						if(player != null) {
							if(chosen == null) { // If this is the first cycle
								chosen = player; // Set creature
								best = le.getLocation(); // Set location
								continue;
							}
							else { // This is the 2nd or more cycle
								double chosenDiff = loc.distance(best); // The location difference between the chosen entity and the location we are trying for
								double challengeDiff = loc.distance(le.getLocation()); // The location difference between the challenger entity and the location we are trying for
								if(chosenDiff > challengeDiff) { // If the challeneger is closer to the location than the chosen
									chosen = player; // Set creature
									best = le.getLocation(); // Set location
								}
								continue;
							}
						}
					}
				}
			}
		}
		return chosen;
	}
	
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		
		ArenaPlayer player = GlobalW.getArenaPlayer(p);
		
		// THIS STUPID PIECE OF SHIT CODE NEEDS TO BE HERE BECAUSE BUKKIT IS JANK AS FUCK
		if(e.getHand() == EquipmentSlot.HAND) {
			if(e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
				// Verify that this is an arenaplayer
				if(player != null) {
					// Is the player holding an ability item
					if(player.isHoldingAbilityItem()) {
						AbilityItem item = player.getAbilityItemInHand();
						Ability ability = item.getOwner();
						AbilityTarget aTarget = ability.getTargetType();
						// This is a self-buff
						if(aTarget.getTargetType() == AbilityTarget.TargetType.SELF) {
							item.useAbility(player, player);
						}
						// This will affect allies around
						else if(aTarget.getTargetType() == AbilityTarget.TargetType.AOE_SELF) {
							Location loc = p.getLocation();
							List<ArenaEntity> affected = getArenaPlayersInRangeToLocation(loc, aTarget.getAbilityRange()); // Gets the arena entities near location
							item.useAbility(player, affected);
						}
						// This will affect a single enemy
						else if(aTarget.getTargetType() == AbilityTarget.TargetType.SINGLE_ENEMY) {
							if(p.getTargetBlockExact(aTarget.getCastRange()) != null) {
								Location loc = p.getTargetBlockExact(aTarget.getCastRange()).getLocation();
								ArenaCreature creature = getClosestArenaCreatureToLocation(loc);
								item.useAbility(player, creature);
							}
						}
						// This will affect an aoe of enemies within you selection
						else if(aTarget.getTargetType() == AbilityTarget.TargetType.AOE_ENEMIES) {
							if(p.getTargetBlockExact(aTarget.getCastRange()) != null) {
								Location loc = p.getTargetBlockExact(aTarget.getCastRange()).getLocation();
								List<ArenaEntity> affected = getArenaCreaturesInRangeToLocation(loc, aTarget.getAbilityRange());
								item.useAbility(player, affected);
							}
						}
						// This will affect a single ally
						else if(aTarget.getTargetType() == AbilityTarget.TargetType.SINGLE_ALLY) {
							if(p.getTargetBlockExact(aTarget.getCastRange()) != null) {
								Location loc = p.getTargetBlockExact(aTarget.getCastRange()).getLocation();
								ArenaPlayer p1 = getClosestArenaPlayerToLocation(loc);
								item.useAbility(player, p1);
							}
						}
						// This will affect an aoe of allies within your selection
						else if(aTarget.getTargetType() == AbilityTarget.TargetType.AOE_ALLIES) {
							if(p.getTargetBlockExact(aTarget.getCastRange()) != null) {
								Location loc = p.getTargetBlockExact(aTarget.getCastRange()).getLocation();
								List<ArenaEntity> affected = getArenaPlayersInRangeToLocation(loc, aTarget.getAbilityRange());
								item.useAbility(player, affected);
							}
						}
						// This will affect anyone
						else if(aTarget.getTargetType() == AbilityTarget.TargetType.SINGLE_ANY) {
							if(p.getTargetBlockExact(aTarget.getCastRange()) != null) {
								Location loc = p.getTargetBlockExact(aTarget.getCastRange()).getLocation();
								ArenaEntity entity = getClosestArenaEntityToLocation(loc);
								item.useAbility(player, entity);
							}
						}
						// This will affect annyone within your selection
						else if(aTarget.getTargetType() == AbilityTarget.TargetType.AOE_ANY) {
							if(p.getTargetBlockExact(aTarget.getCastRange()) != null) {
								Location loc = p.getTargetBlockExact(aTarget.getCastRange()).getLocation();
								List<ArenaEntity> affected = getArenaEntitiesInRangeToLocation(loc, aTarget.getAbilityRange());
								item.useAbility(player, affected);
							}
						}
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		
		ArenaPlayer player = GlobalW.getArenaPlayer(p);
		
		// THIS STUPID PIECE OF SHIT CODE NEEDS TO BE HERE BECAUSE BUKKIT IS JANK AS FUCK
		if(e.getHand() == EquipmentSlot.HAND) {
			// Verify that this is an arenaplayer
			if(player != null) {
				// Is the player holding an ability item
				if(player.isHoldingAbilityItem()) {
					AbilityItem item = player.getAbilityItemInHand();
					Ability ability = item.getOwner();
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
							AbilityTarget.TargetType targetType = ability.getTargetType().getTargetType();
							//If the ability can target this creature.
							if(targetType == AbilityTarget.TargetType.SINGLE_ENEMY || targetType == AbilityTarget.TargetType.AOE_ENEMIES ||
									targetType == AbilityTarget.TargetType.SINGLE_ANY || targetType == AbilityTarget.TargetType.AOE_ANY ) {
								item.useAbility(player, creature);
								e.setCancelled(true);
							}
						}
					}
				}
			}
		}
	}

}
