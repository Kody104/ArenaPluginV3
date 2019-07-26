package com.gmail.jpk.stu.timers;

import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.Entities.ArenaEntity;
import com.gmail.jpk.stu.Entities.ArenaPlayer;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;

public class StatusEffectTimer extends BukkitRunnable {

	private ArenaEntity entity; // The entity that is being affected by the status effect
	private StatusEffect statusEffect; // The status effect
	
	public StatusEffectTimer(ArenaEntity entity, StatusEffect statusEffect) {
		this.entity = entity;
		this.statusEffect = statusEffect;
	}
	
	
	@Override
	public void run() {
		entity.removeStatusEffect(statusEffect);
		
		if(entity instanceof ArenaPlayer) {
			ArenaPlayer player = (ArenaPlayer) entity;
			GlobalW.toPlayer(player.getmPlayer(), "The " + statusEffect.getType() + " effect has expired!");
		}
	}
	
	
}