package com.gmail.jpk.stu.timers;

import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.jpk.stu.Entities.ArenaEntity;
import com.gmail.jpk.stu.abilities.DamageType;
import com.gmail.jpk.stu.abilities.StatusEffect;
import com.gmail.jpk.stu.arena.GlobalW;

public class BleedCountdownTimer extends BukkitRunnable {

	private ArenaEntity entity;
	private StatusEffect owner;
	
	public BleedCountdownTimer(ArenaEntity entity, StatusEffect owner) {
		this.entity = entity;
		this.owner = owner;
	}
	
	@Override
	public void run() {
		if(entity.hasStatusEffectType(StatusEffect.StatusEffectType.BLEED)) {
			new BleedCountdownTimer(entity, owner).runTaskLater(GlobalW.getPlugin(), 20);
		}
		
		double damage = (entity.getMaxHp() * owner.getPow());
		entity.takeDamage(damage, DamageType.PHYSICAL);
	}

}
