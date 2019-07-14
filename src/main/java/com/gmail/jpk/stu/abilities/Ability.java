package com.gmail.jpk.stu.abilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Ability {
	
	public enum AbilityType {
		SELF, ALLY, ENEMY;
	}
	
	private String name;
	private AbilityType type; // The type of ability this is.
	private int lvl; // Level of the ability
	private boolean onCooldown; // Is this ability castable?
	private long cooldown; // The duration of cooldown for this ability
	private ChatColor color; // May or may not be implemented. Thinking about it
	private List<StatusEffect> allStatusEffects; // All the status effects this ability does.
	private double baseDmg; // The base damage the ability will do.
	private double atkScale; // If this ability scales with atk, use this.
	private double magScale; // If this ability scales with mag, use this.
	private double amrScale; // If this ability scales with amr, use this.
	private double resScale; // If this ability scales with res, use this.
	private double hpScale; // If this ability scales with hp, use this.
	
	public Ability(String name, AbilityType type, long cooldown, ChatColor color, double baseDmg, double atkScale, double magScale, double amrScale, double resScale, double hpScale, StatusEffect... effects) {
		this.name = name;
		this.type = type;
		this.lvl = 0;
		this.onCooldown = false;
		this.cooldown = cooldown;
		this.color = color;
		this.allStatusEffects = new ArrayList<StatusEffect>();
		for(StatusEffect e : effects) {
			allStatusEffects.add(e);
		}
		this.baseDmg = baseDmg;
		this.atkScale = atkScale;
		this.magScale = magScale;
		this.amrScale = amrScale;
		this.resScale = resScale;
		this.hpScale = hpScale;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public AbilityType getType() {
		return type;
	}

	public void setType(AbilityType type) {
		this.type = type;
	}

	public int getLvl() {
		return lvl;
	}

	public void setLvl(int lvl) {
		this.lvl = lvl;
	}

	public boolean isOnCooldown() {
		return onCooldown;
	}

	public void setOnCooldown(boolean onCooldown) {
		this.onCooldown = onCooldown;
	}

	public long getCooldown() {
		return cooldown;
	}

	public void setCooldown(long cooldown) {
		this.cooldown = cooldown;
	}

	public ChatColor getColor() {
		return color;
	}

	public void setColor(ChatColor color) {
		this.color = color;
	}

	public List<StatusEffect> getAllStatusEffects() {
		return allStatusEffects;
	}

	public void setAllStatusEffects(List<StatusEffect> allStatusEffects) {
		this.allStatusEffects = allStatusEffects;
	}

	public double getBaseDmg() {
		return baseDmg;
	}

	public void setBaseDmg(double baseDmg) {
		this.baseDmg = baseDmg;
	}

	public double getAtkScale() {
		return atkScale;
	}

	public void setAtkScale(double atkScale) {
		this.atkScale = atkScale;
	}

	public double getMagScale() {
		return magScale;
	}

	public void setMagScale(double magScale) {
		this.magScale = magScale;
	}

	public double getAmrScale() {
		return amrScale;
	}

	public void setAmrScale(double amrScale) {
		this.amrScale = amrScale;
	}

	public double getResScale() {
		return resScale;
	}

	public void setResScale(double resScale) {
		this.resScale = resScale;
	}

	public double getHpScale() {
		return hpScale;
	}

	public void setHpScale(double hpScale) {
		this.hpScale = hpScale;
	}
}
