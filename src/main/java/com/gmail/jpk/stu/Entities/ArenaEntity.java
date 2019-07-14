package com.gmail.jpk.stu.Entities;

import java.util.ArrayList;
import java.util.List;

import com.gmail.jpk.stu.abilities.StatusEffect;

public abstract class ArenaEntity {
	
	private double maxHp; // The max hp of this entity
	private double curHp; // This current hp of this entity
	private double atk; // The attack stat of this entity.
	private double mag; // The magic stat of this entity.
	private double def; // The defense stat of this entity.
	private double res; // The resistance stat of this entity.
	private double critHitChance; // The critical hit chance stat of this entity. 
	private double critHitMultiplier; // The critical hit multiplier stat of this entity.
	private int cdr; // The cooldown reduction stat of this entity.
	private int tenacity; // The tenacity stat of this entity.
	private List<StatusEffect> allStatusEffects; // The current status effects affecting this entity.
	
	public ArenaEntity() {
		allStatusEffects = new ArrayList<StatusEffect>();
	}
	
	public ArenaEntity(double Hp, double atk, double mag, double def, double res, double critchance, double critmulti, int cdr, int tenacity) {
		this.maxHp = Hp;
		this.curHp = Hp;
		this.atk = atk;
		this.mag = mag;
		this.def = def;
		this.res = res;
		this.critHitChance = critchance;
		this.critHitMultiplier = critmulti;
		this.cdr = cdr;
		this.tenacity = tenacity;
		allStatusEffects = new ArrayList<StatusEffect>();
	}

	public double getMaxHp() {
		return maxHp;
	}

	public void setMaxHp(double maxHp) {
		this.maxHp = maxHp;
	}

	public double getCurHp() {
		return curHp;
	}

	public void setCurHp(double curHp) {
		this.curHp = curHp;
	}

	public double getAtk() {
		return atk;
	}

	public void setAtk(double atk) {
		this.atk = atk;
	}

	public double getMag() {
		return mag;
	}

	public void setMag(double mag) {
		this.mag = mag;
	}

	public double getDef() {
		return def;
	}

	public void setDef(double def) {
		this.def = def;
	}

	public double getRes() {
		return res;
	}

	public void setRes(double res) {
		this.res = res;
	}

	public double getCritHitChance() {
		return critHitChance;
	}

	public void setCritHitChance(double critHitChance) {
		this.critHitChance = critHitChance;
	}

	public double getCritHitMultiplier() {
		return critHitMultiplier;
	}

	public void setCritHitMultiplier(double critHitMultiplier) {
		this.critHitMultiplier = critHitMultiplier;
	}

	public int getCdr() {
		return cdr;
	}

	public void setCdr(int cdr) {
		this.cdr = cdr;
	}

	public int getTenacity() {
		return tenacity;
	}

	public void setTenacity(int tenacity) {
		this.tenacity = tenacity;
	}

	public List<StatusEffect> getAllStatusEffects() {
		return allStatusEffects;
	}

	public void setAllStatusEffects(List<StatusEffect> allStatusEffects) {
		this.allStatusEffects = allStatusEffects;
	}
}
