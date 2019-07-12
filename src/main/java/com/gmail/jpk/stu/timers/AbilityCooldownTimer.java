package com.gmail.jpk.stu.timers;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.abilities.Ability;

public class AbilityCooldownTimer extends BukkitRunnable {

	private Player sender; // The owner of the ability
	private Ability owner; // The ability that this cooldowntimer is attached to.
	
	
	/**
	 * Call this after you use an ability. This will reset the ability.
	 * (Ex. new AbilityCooldownTimer(arena_player, player_ability).runTaskLater(plugin, player_ability.getCooldown());)
	 * @param sender	The ability's user.
	 * @param owner		The ability being used.
	 */
	public AbilityCooldownTimer(Player sender, Ability owner) {
		this.sender = sender;
		this.owner = owner;
	}
	
	@Override
	public void run() {
		owner.setOnCooldown(false); // Set the ability back to usable.
		sender.sendMessage("Your " + owner.getName() + " ability is off of cooldown!"); // Notify the player that they can use their ability again.
	}

	public Player getSender() {
		return sender;
	}

	public void setSender(Player sender) {
		this.sender = sender;
	}

	public Ability getOwner() {
		return owner;
	}

	public void setOwner(Ability owner) {
		this.owner = owner;
	}

}
