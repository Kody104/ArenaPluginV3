package com.gmail.jpk.stu.timers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.Entities.ArenaEntity;
import com.gmail.jpk.stu.abilities.Ability;
import com.gmail.jpk.stu.arena.GlobalW;

public class AbilityCooldownTimer extends BukkitRunnable {

	private ArenaEntity sender; // The owner of the ability
	private Ability owner; // The ability that this cooldowntimer is attached to.
	
	
	/**
	 * Call this after you use an ability. This will reset the ability.
	 * (Ex. new AbilityCooldownTimer(arena_player, player_ability).runTaskLater(plugin, player_ability.getCooldown());)
	 * @param entity	The ability's user.
	 * @param owner		The ability being used.
	 */
	public AbilityCooldownTimer(ArenaEntity entity, Ability owner) {
		this.sender = entity;
		this.owner = owner;
	}
	
	@Override
	public void run() {
		owner.setOnCooldown(false); // Set the ability back to usable.
		// If the entity is not in the arenacreature list then we know that they are a player!
		if(GlobalW.getArenaCreature(sender.getLivingEntity()) == null) {
			Player p = (Player) sender.getLivingEntity();
			p.sendMessage("Your " + owner.getName() + " ability is off of cooldown!"); // Notify the player that they can use their ability again.
		}
	}

	public ArenaEntity getSender() {
		return sender;
	}

	public void setSender(ArenaEntity sender) {
		this.sender = sender;
	}

	public Ability getOwner() {
		return owner;
	}

	public void setOwner(Ability owner) {
		this.owner = owner;
	}

}
