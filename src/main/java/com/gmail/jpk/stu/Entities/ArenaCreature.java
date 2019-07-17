package com.gmail.jpk.stu.Entities;

import org.bukkit.entity.LivingEntity;

import com.gmail.jpk.stu.abilities.PassiveAbility;

public class ArenaCreature extends ArenaEntity {
	
	private LivingEntity entityType;
	private int lvl;
	private PassiveAbility passive;
	
	public ArenaCreature(LivingEntity entityType, int lvl, PassiveAbility passive) {
		this.entityType = entityType;
		this.lvl = lvl;
		this.passive = passive;
	}
	
	/**
	 * Returns how much experience an ArenaEntity drops.
	 * @param bonus any bonus experience dropped from this ArenaEntity
	 * @return the amount of experience dropped
	 */
	public int getExpDrop(int bonus) {
		return (int) (bonus + (15 + (0.03 * Math.pow(lvl, 2))));
	}

	public LivingEntity getEntityType() {
		return entityType;
	}

	public void setEntityType(LivingEntity entityType) {
		this.entityType = entityType;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public PassiveAbility getPassive() {
		return passive;
	}

	public void setPassive(PassiveAbility passive) {
		this.passive = passive;
	}
}
