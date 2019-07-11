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
