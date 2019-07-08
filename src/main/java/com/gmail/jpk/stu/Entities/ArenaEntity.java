package com.gmail.jpk.stu.entities;

public abstract class ArenaEntity {
	
	private double maxHp;
	private double curHp;
	private double atk;
	private double mag;
	private double def;
	private double res;
	private double critHitChance;
	private double critHitMultiplier;
	private int cdr;
	private int tenacity;
	
	public ArenaEntity() {
		
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
}
